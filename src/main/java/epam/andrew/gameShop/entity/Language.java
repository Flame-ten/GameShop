package epam.andrew.gameShop.entity;

import java.util.Objects;

public class Language {
    private Integer id;
    private String language;
    private boolean deleted;

    public Language() {

    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", language='" + language + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language1 = (Language) o;
        return Objects.equals(id, language1.id) &&
                Objects.equals(language, language1.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language);
    }

    public Language(Integer id, String language) {
        this.id = id;
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
