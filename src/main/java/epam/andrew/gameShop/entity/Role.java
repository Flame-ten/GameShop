package epam.andrew.gameShop.entity;

import epam.andrew.gameShop.enums.RoleName;

import static epam.andrew.gameShop.util.Constant.*;

public class Role extends LocaleName {
    private Integer id;
    private String name;
    private boolean deleted;

    public Role(Integer id) {
        setId(id);
        setRoleName(id);
    }

    public Role() {

    }

    private void setRoleName(Integer id) {
        RoleName roleName;
        switch (id) {
            case INDEX_1:
                roleName = RoleName.USER;
                break;
            case INDEX_2:
                roleName = RoleName.GUEST;
                break;
            case INDEX_3:
                roleName = RoleName.ADMIN;
                break;
            default:
                roleName = RoleName.GUEST;
        }
        name = roleName.toString();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
