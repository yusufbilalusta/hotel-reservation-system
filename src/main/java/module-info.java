module proje.otelrezervasyonuygulamasi {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens proje.otelrezervasyonuygulamasi to javafx.fxml;
    exports proje.otelrezervasyonuygulamasi;
}