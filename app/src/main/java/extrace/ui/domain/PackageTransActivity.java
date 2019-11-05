package extrace.ui.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import extrace.loader.TransPackageLoader;
import extrace.loader.UserInfoLoader;
import extrace.misc.model.TransPackage;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;
import zxing.util.CaptureActivity;

public class PackageTransActivity extends AppCompatActivity implements IDataAdapter<TransPackage> {

    public static final int REQUEST_SearchPid = 666;
    ImageView ivScan;
    EditText etPackageId;
    Button btOk;
    TransPackageLoader mloader;

    String Pid;
    UserInfo user;
    private ExTraceApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_trans);
        ivScan = (ImageView) findViewById(R.id.package_trans_iv_scan);
        etPackageId = (EditText) findViewById(R.id.package_trans_et_pid);
        btOk = (Button) findViewById(R.id.package_trans_bt_ok);
        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search();
            }
        });
        app = (ExTraceApplication) getApplication();
        user = app.getLoginUser();


        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pid = etPackageId.getText().toString();
                mloader = new TransPackageLoader(PackageTransActivity.this,PackageTransActivity.this);
                mloader.Transfer(Pid,user.getUID()+"");

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode){
                    /*
                     * 查包
                     */
                    case REQUEST_SearchPid:

                        if (data.hasExtra("BarCode")) {			//打包
                            String id = data.getStringExtra("BarCode");
                            Pid = id;
                            etPackageId.setText(id);
//                            try {
//                                mLoader = new ExpressListLoader(this, this);
//                                mLoader.LoadExpressListInPackage(id);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                        }
                        break;


                }
                break;
            default:
                break;
        }
    }

    private void Search(){
        Intent intent = new Intent();
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_SearchPid);
    }

    @Override
    public TransPackage getData() {
        return null;
    }

    @Override
    public void setData(TransPackage data) {

    }

    @Override
    public void notifyDataSetChanged() {
        UserInfoLoader mLoader = new UserInfoLoader(PackageTransActivity.this);
        //每次点击新建Loader,以刷新数据
        mLoader.LoadUserInfoByID(user.getUID()+"",user.getPWD());
    }


}
