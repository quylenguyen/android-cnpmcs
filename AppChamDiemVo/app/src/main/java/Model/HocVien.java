package Model;

/**
 * Created by NhanVT3 on 10/26/2017.
 */

public class HocVien {
    public String _Ten;
    public String _NgaySinh;
    public int _Tuoi;
    public String _CMND;
    public String _CapDaiHienTai;
    public static HocVien instance;
    public static HocVien getInstance(){
        if(instance == null)
        {
            instance = new HocVien();
        }
        return instance;
    }

}
