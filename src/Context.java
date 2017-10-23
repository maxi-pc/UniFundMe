/**
 * Created by Maxi on 10/23/2017.
 */
public class Context {
    private final static Context instance = new Context();
    public static Context getInstance() {
        return instance;
    }


    private MainController tabRough;
    public void setTabRough(MainController tabRough) {
        this.tabRough=tabRough;
    }

    public MainController getTabRough() {
        return tabRough;
    }
}
