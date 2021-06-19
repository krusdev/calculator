package ru.geekbrains.calculator;


import android.widget.EditText;
import android.widget.TextView;

public class CalculationLogic {

    private Double arg1 = null;
    private Double arg2 = null;
    private Double result;
    private String currentAction = null;
    private static final String ADDITION = "+";
    private static final String SUBTRACTION = "-";
    private static final String MULTIPLICATION = "×";
    private static final String DIVISION = "÷";
    private static final String PERCENTAGE = "%";
    private static final String EQUALS = "=";
    private static final Double ZERO = 0.0;

    private EditText numEnterTextView;
    private TextView resultTextView;


    public void setNumEnterTextView(EditText numEnterTextView) {
        this.numEnterTextView = numEnterTextView;
    }

    public void setResultTextView(TextView resultTextView) {
        this.resultTextView = resultTextView;
    }

    public void onCalculationButtonPressed(int buttonId) {
        if (buttonId == R.id.button_addition) {
            calculationButtonAction(ADDITION);
        }
        if (buttonId == R.id.button_subtraction) {
            calculationButtonAction(SUBTRACTION);
        }
        if (buttonId == R.id.button_division) {
            calculationButtonAction(DIVISION);
        }
        if (buttonId == R.id.button_multiplication) {
            calculationButtonAction(MULTIPLICATION);
        }
        if (buttonId == R.id.button_percentage) {
            calculationButtonAction(PERCENTAGE);
        }
        if (buttonId == R.id.button_equals) {
            calculationButtonAction(EQUALS);
        }
        if (buttonId == R.id.button_delete) {
            if(numEnterTextView.length() > 0) {
                numEnterTextView.getText().delete(numEnterTextView.length() - 1, numEnterTextView.length());
            }
        }
        if (buttonId == R.id.button_clear) {
            numEnterTextView.getText().clear();
            resultTextViewClear();
            arg1 = null;
            arg2 = null;
            result = null;
        }
    }

    private void calculationButtonAction(String mathematicalAction) {

        String argument;

        if (mathematicalAction.equals(PERCENTAGE)) {
            if (result != null & numEnterTextView.getText().length() == 0) {
                resultTextViewClear();
                arg1 = result;
                result = arg1 / 100;
                numEnterTextView.getText().clear();
                resultTextView.append(String.format("%s %s %s %s", arg1, mathematicalAction, EQUALS, result));
                arg1 = null;

            } else if (arg1 == null) {
                resultTextViewClear();
                argument = String.valueOf(numEnterTextView.getText());
                arg1 = Double.valueOf(argument);
                result = arg1 / 100;
                numEnterTextView.getText().clear();
                resultTextView.append(String.format("%s", result));
                arg1 = null;
            } else {
                argument = String.valueOf(numEnterTextView.getText());
                arg2 = Double.valueOf(argument);
                numEnterTextView.getText().clear();
                result = arg1 + (arg1 * arg2 / 100);
                resultTextView.append(String.format("%s %s %s %s", arg2, mathematicalAction, EQUALS, result));
            }

        } else if (mathematicalAction.equals(EQUALS) & arg1 != null) {
            try {
                argument = String.valueOf(numEnterTextView.getText());
                arg2 = Double.valueOf(argument);
                numEnterTextView.getText().clear();
                mathAction(currentAction, arg1, arg2);
                resultTextView.append(String.format("%s %s %s", arg2, mathematicalAction, result));
                arg1 = null;
                arg2 = null;
            } catch (NumberFormatException e) {
                numEnterTextView.getText().clear();
            }

        } else if (result != null & !mathematicalAction.equals(EQUALS)) {
            try {
                if (numEnterTextView.getText().length() == 0) {
                    arg1 = result;
                    resultTextViewClear();
                    resultTextView.append(String.format("%s %s ", arg1, mathematicalAction));
                    currentAction = mathematicalAction;
                    result = null;
                } else {
                    resultTextView.getEditableText().clear();
                    argument = String.valueOf(numEnterTextView.getText());
                    arg1 = Double.valueOf(argument);
                    numEnterTextView.getText().clear();
                    resultTextView.append(String.format("%s %s ", arg1, mathematicalAction));
                    currentAction = mathematicalAction;
                }
            } catch (NumberFormatException e) {
                numEnterTextView.getText().clear();
            }

        } else if (arg1 == null & !mathematicalAction.equals(EQUALS)) {
            try {
                argument = String.valueOf(numEnterTextView.getText());
                arg1 = Double.valueOf(argument);
                numEnterTextView.getText().clear();
                resultTextView.append(String.format("%s %s ", arg1, mathematicalAction));
                currentAction = mathematicalAction;
            } catch (NumberFormatException e) {
                arg1 = ZERO;
                resultTextView.append(String.format("%s %s ", arg1, mathematicalAction));
                currentAction = mathematicalAction;
            }

        } else {
            try {
                argument = String.valueOf(numEnterTextView.getText());
                arg2 = Double.valueOf(argument);
                numEnterTextView.getText().clear();
                mathAction(currentAction, arg1, arg2);
                currentAction = mathematicalAction;
                arg1 = result;
                resultTextViewClear();
                resultTextView.append(String.format("%s %s ", arg1, mathematicalAction));
                arg2 = null;
                result = null;
            } catch (NumberFormatException e) {
                numEnterTextView.getText().clear();
            }

        }
    }

    private void mathAction(String action, Double arg1, Double arg2) {
        switch (action) {
            case ADDITION:
                result = arg1 + arg2;
                break;

            case SUBTRACTION:
                result = arg1 - arg2;
                break;

            case DIVISION:
                result = arg1 / arg2;
                break;

            case MULTIPLICATION:
                result = arg1 * arg2;
                break;

            /*case PERCENTAGE:
                if (arg2 != null) {
                    result = arg1 + (arg1 * arg2 / 100);
                } else {
                    result = arg1 / 100;
                }
                break;*/
        }
    }

    private void resultTextViewClear() {
        if(resultTextView.length() > 0) {
            resultTextView.getEditableText().clear();
        }
    }


}

//todo Division by zero

//todo отработаьь: если после уже нажатого знака хочу поменять, то при нажатии на другой знак, должен меняться и curr action перезаписать

//todo Decimal format

//todo кол-во знаков в одном числе