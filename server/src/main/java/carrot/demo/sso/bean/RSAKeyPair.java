package carrot.demo.sso.bean;

public class RSAKeyPair {
    private long id;

    private String app;

    private String publicKey;

    private String privateKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
