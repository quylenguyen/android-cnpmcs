package Model;

/**
 * Created by NhanVT3 on 10/26/2017.
 */

public class HocVien {
    public String _Ten;
    public String _NgaySinh;
    public String _DonVi;
    public String _DaiHienTai;
    public int _DiemNoiDung1;
    public int _DiemNoiDung2;
    public int _DiemNoiDung3;
    public int _DiemNoiDung4;
    public int _DiemNoiDung5;
    public int _TongDiem;

    public HocVien(String _Ten, String _NgaySinh, String _DonVi, String _DaiHienTai, int _DiemNoiDung1, int _DiemNoiDung2, int _DiemNoiDung3, int _DiemNoiDung4, int _DiemNoiDung5, int _TongDiem) {
        this._Ten = _Ten;
        this._NgaySinh = _NgaySinh;
        this._DonVi = _DonVi;
        this._DaiHienTai = _DaiHienTai;
        this._DiemNoiDung1 = _DiemNoiDung1;
        this._DiemNoiDung2 = _DiemNoiDung2;
        this._DiemNoiDung3 = _DiemNoiDung3;
        this._DiemNoiDung4 = _DiemNoiDung4;
        this._DiemNoiDung5 = _DiemNoiDung5;
        this._TongDiem = _TongDiem;
    }

    public HocVien() {
    }

    public HocVien(String _Ten, String _NgaySinh, String _DonVi, String _DaiHienTai) {
        this._Ten = _Ten;
        this._NgaySinh = _NgaySinh;
        this._DonVi = _DonVi;
        this._DaiHienTai = _DaiHienTai;
    }
}
