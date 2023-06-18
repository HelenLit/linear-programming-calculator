package swingUI;

public class InputInfo {
    private static int equations = 1;
    private static int variables = 1;

    public static void setEquations(int equations) {
        InputInfo.equations = equations;
    }

    public static void setVariables(int variables) {
        InputInfo.variables = variables;
    }

    public static int getEquations() {
        return equations;
    }

    public static int getVariables() {
        return variables;
    }
}
