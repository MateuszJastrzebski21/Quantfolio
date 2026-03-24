module org.example.mas_zao_jastrzebski_s27397 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.mas_zao_jastrzebski_s27397 to javafx.fxml;
    //exports org.example.mas_zao_jastrzebski_s27397;
    exports org.example.mas_zao_jastrzebski_s27397.app;
    opens org.example.mas_zao_jastrzebski_s27397.app to javafx.fxml;
    exports org.example.mas_zao_jastrzebski_s27397.controller;
    opens org.example.mas_zao_jastrzebski_s27397.controller to javafx.fxml;
}