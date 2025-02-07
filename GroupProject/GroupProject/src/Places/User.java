package Places;

public class User {
    private int id;
    private String place;
    private String name;
    private String surname;
    private String gender;
    private String phone;

    public User(int id, String place , String name, String surname, String gender, String phone) {
        this.id = id;
        this.place = place;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.phone = phone;
    }
    public String getPlace(){ return place;}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", place=" + place + '\''+
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
