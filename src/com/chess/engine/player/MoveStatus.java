package com.chess.engine.player;

public enum MoveStatus {
    DONE{
        @Override
        public boolean isDone() {
            return true;
        }
    },
    ILLEGAL_Move {
        @Override
        public boolean isDone() {
            return false;
        }
    };
    public abstract boolean isDone();
}
