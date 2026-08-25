package kdb;

/** Represents the commands understood by KDB. */
public enum CommandType {
    BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, UNKNOWN;

    /**
     * Maps a raw command word to a CommandType, defaulting to UNKNOWN
     * for anything not recognised.
     */
    /**
     * Maps a raw command word to a command type.
     *
     * @param word command text entered by the user
     * @return the matching command, or {@code UNKNOWN} when there is no match
     */
    public static CommandType fromWord(String word) {
        try {
            return CommandType.valueOf(word.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
