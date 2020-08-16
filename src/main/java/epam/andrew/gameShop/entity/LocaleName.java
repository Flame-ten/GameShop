package epam.andrew.gameShop.entity;

import java.util.Locale;

public class LocaleName {
    private String ruName;
    private String enName;

    @Override
    public String toString() {
        return "LocaleName{" +
                "ruName='" + ruName + '\'' +
                ", enName='" + enName + '\'' +
                '}';
    }

    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getName(Locale locale) {
        if (locale != null && locale.getLanguage().equals("ru")) {
            return ruName;
        } else {
            return enName;
        }
    }


}
