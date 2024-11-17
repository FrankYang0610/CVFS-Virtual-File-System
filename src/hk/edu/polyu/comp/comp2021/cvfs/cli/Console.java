package hk.edu.polyu.comp.comp2021.cvfs.cli;

import hk.edu.polyu.comp.comp2021.cvfs.globalexceptions.CVFS_Exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h2>The {@code Console} Class</h2>
 * <b>Console</b> is the frontend part, and it is the mixed component between the View and Controller.
 */
public final class Console {
    /**
     * The input stream of the console. Generally, it should be {@code System.in}.
     */
    private final Scanner scanner;

    /**
     * The constructor of the console.
     * <p>
     * Note that this method does not boot the console. It merely associates the needed resources, such as input and output streams.
     */
    public Console() {
        scanner = new Scanner(System.in);
    }

    /**
     * Boot the console.
     */
    public void boot() {
        printFirstMessage();
    }

    /**
     * Get the next command from the input stream, parse it, and return back to the controller.
     * Please also see the {@code String[] parse(String rawCommand)} method.
     * <p>
     * This method is controlled by the controller after the console boots.
     * @param workingDirectoryPath the current working directory address provided by the Controller.
     * @return the parsed user command.
     */
    public String[] getNextCommand(String workingDirectoryPath) {
        System.out.println();
        if (!workingDirectoryPath.isEmpty()) {
            System.out.print("admin@CVFS" + " " + workingDirectoryPath + " " + ">>" + " ");
        } else {
            System.out.print("admin@CVFS" + " " + ">>" + " ");
        }

        String rawCommand = scanner.nextLine();
        return parse(rawCommand);
    }

    /**
     * Print the information after an operation is executed. This should always be a good news~
     * @param result the result of the operation's execution.
     */
    public void printInformation(String result) {
        System.out.println(result);
    }

    /**
     * Print the exception information to the user.
     * @param e the exception
     */
    public void printErrorStream(CVFS_Exception e) {
        System.out.println(e.getMessage());
    }

    /**
     * Print the first message using the {@code System.out} stream.
     * <p>
     * The content should be edited well.
     */
    private void printFirstMessage() {
        System.out.println("""
                Welcome to CVFS - COMP Virtual File System, Project Bayagi Dogan ('Peregrine Falcon')\s
                Group members:\s
                    Yang Xikun Frank (overall design and coding)\s
                    Yang Jinkun Jim (model checking and improving)\s
                    Ren Yixiao Zachary (test engineer)\s
                    Arda Eren (report and user manual)\s
                Version: 1.0, Build Nov 13, 2024\n
                Make a difference from today! Protect planet's most extraordinary places – mangroves, forests, wetlands and coasts – save the home to thousands of unique birds.""");
    }

    /**
     * Parse the raw command (denoted as {@code rawCommand}) from the input stream.
     *
     * <h4>Processing Mechanism</h4>
     * <ul>
     *     <li>All spaces and whitespace characters before and after {@code rawCommand} will be removed at anytime.</li>
     *     <li>Spaces will be removed by default, and the different components of {@code rawCommand} are separated by spaces.</li>
     *     <li>Spaces within quotes ({@code ""}) and backticks ({@code ``}) will [not] be removed.</li>
     *     <li>Quote pairs will be kept in the final result, but backtick pairs will not.</li>
     *     <li>For valid input, a correct output is guaranteed. For invalid input (such as {@code `"a`"}), the result may be undefined.</li>
     * </ul>
     *
     * <h4>Regex usage</h4>
     * <blockquote><pre>
     *     \"[^\"]*\"   // A pair or quotes and all characters within them.
     *     |            // Logic or operator, indicating both the former and latter match.
     *     `[^`]*`      // A pair of backticks and all characters within them.
     *     |            // Logic or operator, indicating both the former and latter match.
     *     [^\"`]+      // Any char(s) except quote and backtick.
     * </pre></blockquote>
     *
     * <h4>Examples</h4>
     * <blockquote><pre>
     *     a b          ->      [a, b]
     *     a   b        ->      [a, b]
     *     "a  b"       ->      ["a  b"]
     *     `a  b`       ->      [a  b]
     *     a  b `c d`   ->      [a, b, c d]
     * </pre></blockquote>
     *
     * @param rawCommand the raw command from the input stream.
     * @return the processed command, split into an array.
     * @implNote A very complicated regex system is used.
     *
     */
    private String[] parse(String rawCommand) {
        assert rawCommand != null;
        rawCommand = rawCommand.trim();

        List<String> result = new ArrayList<>();

        Pattern pattern = Pattern.compile("\"[^\"]*\"|`[^`]*`|[^\"`]+");
        Matcher matcher = pattern.matcher(rawCommand);

        while (matcher.find()) {
            String segment = matcher.group();
            if (segment.startsWith("`") && segment.endsWith("`")) {
                result.add(segment.substring(1, segment.length() - 1));
            } else if (segment.startsWith("\"") && segment.endsWith("\"")) {
                result.add(segment);
            } else {
                String[] splits = segment.split("\\s+");
                for (String split: splits) {
                    if (!split.isEmpty()) {
                        result.add(split);
                    }
                }
            }
        }

        return result.toArray(new String[0]);
    }
}
