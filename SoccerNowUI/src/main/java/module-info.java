module com.soccernow.ui.soccernowui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.jetbrains.annotations;
    requires jakarta.validation;
    requires org.hibernate.validator;
    requires okhttp3;

    opens com.soccernow.ui.soccernowui to javafx.fxml;

    opens com.soccernow.ui.soccernowui.dto.user to org.hibernate.validator;
    opens com.soccernow.ui.soccernowui.dto.games to org.hibernate.validator;
    opens com.soccernow.ui.soccernowui.dto to org.hibernate.validator;

    opens com.soccernow.ui.soccernowui.controller to javafx.fxml;

    exports com.soccernow.ui.soccernowui;
    exports com.soccernow.ui.soccernowui.util;
    exports com.soccernow.ui.soccernowui.controller;
    exports com.soccernow.ui.soccernowui.dto;
}
