package Model;

/**
 * Created by vtnhan on 10/27/2017.
 */

public class CapDai {
    public String id;
    public String _TenCapDai;
    public CapDai() {
    }

    public CapDai(String id, String _TenCapDai) {
        this.id = id;
        this._TenCapDai = _TenCapDai;
    }

    public CapDai(String _TenCapDai) {
        this._TenCapDai = _TenCapDai;
    }
}
