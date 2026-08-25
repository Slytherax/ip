package kdb;

public enum CommandType {
    BYE, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, UNKNOWN;

    /**
     * Maps a raw command word to a CommandType, defaulting to UNKNOWN
     * for anything not recognised.
     */
    public static CommandType fromWord(String word) {
        try {
            return CommandType.valueOf(word.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
