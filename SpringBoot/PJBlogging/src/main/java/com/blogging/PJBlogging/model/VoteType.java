package com.blogging.PJBlogging.model;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);
    private int direction;
    private VoteType( int direction) {
        this.direction = direction;
    }

    public int getPriority() {
        return direction;
    }
}
