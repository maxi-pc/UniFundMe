/**
 * Created by Maxi on 10/23/2017.
 */
public class Context2 {
    private final static Context2 instance = new Context2();
    public static Context2 getInstance() {
        return instance;
    }


    private Main tabRough;
    public void setTabRough(Main tabRough) {
        this.tabRough=tabRough;
    }

    public Main getTabRough() {
        return tabRough;
    }
}
