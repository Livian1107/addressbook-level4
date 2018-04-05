package seedu.progresschecker.logic.commands;

//@@author adityaa1998

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_ASSIGNEES;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_GIT_PASSCODE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_GIT_REPO;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_GIT_USERNAME;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TITLE;

import java.io.IOException;

import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.model.issues.Issue;

/**
 * Logins into github from app for issue creation
 */
public class GitLoginCommand extends Command {

    public static final String COMMAND_WORD = "gitlogin";
    public static final String COMMAND_ALIAS = "gl";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " "
            + PREFIX_GIT_USERNAME + "USERNAME "
            + PREFIX_GIT_REPO + "REPOSITORY "
            + PREFIX_GIT_PASSCODE + "PASSCODE ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs into github "
            + "Parameters: "
            + PREFIX_GIT_USERNAME + "USERNAME "
            + PREFIX_GIT_REPO + "REPOSITORY "
            + PREFIX_GIT_PASSCODE + "PASSCODE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GIT_USERNAME + "johndoe "
            + PREFIX_GIT_PASSCODE + "dummy123 "
            + PREFIX_GIT_REPO + "CS2103/main ";
    public static final String MESSAGE_SUCCESS = "You have successfully authenticated github!";
    public static final String MESSAGE_FAILURE = "Oops? Maybe the password or the username is incorrect";

    private final GitDetails toAuthenticate;

    /**
     * Creates an GitDetails object to authenticate with github {@code GitDetails}
     */
    public GitLoginCommand(GitDetails gitDetails) {
        requireNonNull(gitDetails);
        toAuthenticate = gitDetails;
    }
    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.createIssueOnGitHub(toCreate);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

}
