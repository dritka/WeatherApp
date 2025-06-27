package api;

public enum Attributes {
    current,
    condition;

    public enum current_json {
        temp_c,
        feelslike_c,
        wind_kph,
        vis_km;
    };

    public enum condition_json {
        code,
        icon,
        text,
    }
}
