package com.example.appdictionaryghtk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {
    Arabic("Arabic"),
    Bulgarian("Bulgarian"),
    Catalan("Catalan"),
    Chinese_Simplified("Chinese Simplified"),
    Chinese_Traditional("Chinese Traditional"),
    Croatian("Croatian"),
    Czech("Czech"),
    Danish("Danish"),
    Dutch("Dutch"),
    English("English"),
    Finnish("Finnish"),
    French("French"),
    German("German"),
    Greek("Greek"),
    Hebrew("Hebrew"),
    Hindi("Hindi"),
    Hungarian("Hungarian"),
    Indonesian("Indonesian"),
    Italian("Italian"),
    Japanese("Japanese"),
    Korean("Korean"),
    Malay("Malay"),
    Norwegian("Norwegian"),
    Polish("Polish"),
    Portuguese("Portuguese"),
    Romanian("Romanian"),
    Russian("Russian"),
    Slovak("Slovak"),
    Slovenian("Slovenian"),
    Spanish("Spanish"),
    Swedish("Swedish"),
    Tamil("Tamil"),
    Thai("Thai"),
    Turkish("Turkish"),
    Vietnamese("Vietnamese");

    private final String name;
}
