package com.company;

public class FieldSpace {

    private boolean flag;
    private boolean mine;
    private boolean cleared;
    private int warnings;

    FieldSpace(boolean flag, boolean mine, boolean cleared, int warnings) {
        this.flag = flag;
        this.mine = mine;
        this.cleared = cleared;
        this.warnings = warnings;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isCleared() {
        return cleared;
    }

    public void setCleared(boolean cleared) {
        if(!isFlag()) {
            this.cleared = cleared;
        }
    }

    public int getWarnings() {
        return warnings;
    }

    public void incrementWarnings() {
        warnings++;
    }
}
