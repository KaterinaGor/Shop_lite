
package myshop;

import app.App;

public class MyShop {
    public static void main(String[] args){
        if(args.length==0){
            App.isBase = true;
        }else{
            App.isBase = false;
        }
        App app;
        app = new App();
        app.run();
    }
}
