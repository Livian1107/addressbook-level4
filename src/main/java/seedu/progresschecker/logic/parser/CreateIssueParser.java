package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_ASSIGNEES;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.CreateIssue;
import seedu.progresschecker.logic.parser.exceptions.ParseException;
import seedu.progresschecker.model.issues.Assignees;
import seedu.progresschecker.model.issues.Body;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.issues.Labels;
import seedu.progresschecker.model.issues.Milestone;
import seedu.progresschecker.model.issues.Title;


/**
 * Parses input arguments and creates a new CreateIssue object
 */
public class CreateIssueParser implements Parser<CreateIssue> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateIssue parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_ASSIGNEES,
                        PREFIX_MILESTONE, PREFIX_BODY, PREFIX_LABEL);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_ASSIGNEES,
                PREFIX_MILESTONE, PREFIX_BODY, PREFIX_LABEL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateIssue.MESSAGE_USAGE));
        }

        try {
            Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE)).get();
            Set<Assignees> assigneeSet = ParserUtil.parseAssignees(argMultimap.getAllValues(PREFIX_ASSIGNEES));
            Milestone milestone = ParserUtil.parseMilestone(argMultimap.getValue(PREFIX_MILESTONE)).get();
            Body body = ParserUtil.parseBody(argMultimap.getValue(PREFIX_BODY).get());
            Set<Labels> labelSet = ParserUtil.parseLabels(argMultimap.getAllValues(PREFIX_LABEL));

            List<Assignees> assigneesList = new ArrayList<>(assigneeSet);
            List<Labels> labelsList = new ArrayList<>(labelSet);

            Issue issue = new Issue(title, assigneesList, milestone, body, labelsList);

            return new CreateIssue(issue);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
