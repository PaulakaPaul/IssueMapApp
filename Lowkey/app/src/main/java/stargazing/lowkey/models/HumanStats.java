package stargazing.lowkey.models;

public class HumanStats {
    private int Start;
    private int End;
    private float Value;

    public HumanStats(int start, int end, float value) {
        Start = start;
        End = end;
        Value = value;
    }

    public int getStart() {
        return Start;
    }

    public void setStart(int start) {
        Start = start;
    }

    public int getEnd() {
        return End;
    }

    public void setEnd(int end) {
        End = end;
    }

    public float getValue() {
        return Value;
    }

    public void setValue(float value) {
        Value = value;
    }
}
