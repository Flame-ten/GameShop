package epam.andrew.gameShop.entity;

import epam.andrew.gameShop.enums.Status;

import static epam.andrew.gameShop.util.Constant.*;

public class TransactionStatus {
    private int id;
    private String name;
    private boolean deleted;

    public TransactionStatus() {

    }

    public TransactionStatus(int id, String info) {
        this.id = id;
        this.name = info;
    }

    private void setStatus(Integer id) {
        Status statusName;
        switch (id) {
            case INDEX_1:
                statusName = Status.COMPLETE;
                break;
            case INDEX_2:
                statusName = Status.FAILED;
                break;
            case INDEX_3:
                statusName = Status.IN_PROGRESS;
                break;
            default:
                statusName = Status.IN_PROGRESS;
        }
        name = statusName.toString();
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", info='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
