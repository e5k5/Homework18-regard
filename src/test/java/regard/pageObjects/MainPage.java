package regard.pageObjects;



public class MainPage extends AbstractPageWithLMenu{
    private String url;

    public MainPage(String url) {
        this.url = url;
    }
    public String getUrl() {
        return this.url;
    }
    public MainPage open() {
        driver.get(url);
        return this;
    }
}
