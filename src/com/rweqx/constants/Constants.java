package com.rweqx.constants;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;

public abstract class Constants {
    public static final String VERSION = "BETA 5.0";

    public static final String SAVE_FOLDER = "TutorTracker";
    public static final String CLASS_SAVE_FILE = "/classes.json";
    public static final String STUDENT_SAVE_FILE = "/students.json";
    public static final String PAYMENT_SAVE_FILE = "/payments.json";
    public static final String LOG_SAVE_FILE = "/logs.json";

    public static final String NUMBER_REGEX = "\\d*\\.?\\d*";

    //Fonts

    public static final Font BASE_FONT = Font.font("Calibri", FontWeight.BOLD, 16.0);

    public static final LocalDate NULL_DATE = LocalDate.of(2000, 1, 1);
}
