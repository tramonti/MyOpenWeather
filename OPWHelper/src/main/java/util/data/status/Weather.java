package util.data.status;

import java.util.Optional;

/**
 * Created by olesia on 06.05.16.
 * It is a POJO generated from Json
 */
public class Weather {
    /*fields*/
    private int id;
    private String main;
    private String description;
    private String icon;

    /*constructors*/
    public Weather(Optional<Integer> id, Optional<String> main,
                   Optional<String> description, Optional<String> icon) {
        this.id = id.orElse(0);
        this.main = main.orElse("Sorry...");
        this.description = description.orElse("Sorry...");
        this.icon = icon.orElse("01n");
    }

    /*getters*/

    /**
     * @return int weather id
     */
    public int getId() {
        return id;
    }

    /**
     * @return String weather main
     * @throws NullPointerException
     */
    public String getMain() {
        if (main == null) throw new NullPointerException("You didn't set main");
        return main;
    }

    /**
     * @return String weather description
     * @throws NullPointerException
     */
    public String getDescription() {
        if (description == null) throw new NullPointerException("You didn't set description");
        return description;
    }

    /**
     * @return String icon name
     * @throws NullPointerException
     */
    public String getIcon() {
        if (icon == null) throw new NullPointerException("You didn't set icon");
        return icon;
    }

    /*toString*/
    @Override
    public String toString() {
        return "Weather: id - " + id + ", main - " + main + ", description - " + description;
    }
}
