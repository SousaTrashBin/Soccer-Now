open module com.soccernow.ui.soccernowui {
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
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    exports com.soccernow.ui.soccernowui;
    exports com.soccernow.ui.soccernowui.util;
    exports com.soccernow.ui.soccernowui.dto;
    exports com.soccernow.ui.soccernowui.dto.user;
    exports com.soccernow.ui.soccernowui.dto.games;
    exports com.soccernow.ui.soccernowui.dto.tournament;

    exports com.soccernow.ui.soccernowui.controller.game;
    exports com.soccernow.ui.soccernowui.controller.home;
    exports com.soccernow.ui.soccernowui.controller.player;
    exports com.soccernow.ui.soccernowui.controller.referee;
    exports com.soccernow.ui.soccernowui.controller.team;
    exports com.soccernow.ui.soccernowui.controller.tournament;
}
