package ir.moke.bale.model;

public class UserSession {
    private State state = State.START;
    private Long p1;
    private Long p2;

    public UserSession() {
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getP1() {
        return p1;
    }

    public void setP1(Long p1) {
        this.p1 = p1;
    }

    public Long getP2() {
        return p2;
    }

    public void setP2(Long p2) {
        this.p2 = p2;
    }

    public Long calc() {
        return p1 + p2;
    }
}
