package extrace.ui.domain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import extrace.loader.DeliverLoader;
import extrace.loader.ExpressListLoader;
import extrace.misc.model.ExpressSheet;
import extrace.misc.model.UserInfo;
import extrace.net.MyDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.PackageEditActivity;
import extrace.ui.main.PackageEditAdapter;
import extrace.ui.main.R;
import zxing.util.CaptureActivity;

public class PackagePaisongActivity extends AppCompatActivity implements MyDataAdapter<List<ExpressSheet>> {

    private UserInfo user;
    private ExTraceApplication app;
    ListView listView;
    ImageView ivScan;
    private List<ExpressSheet> ExpressList;
    private ExpressListLoader mLoader;
    DeliverLoader deliverLoader;

    public static final int REQUEST_PAISONG = 106;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_paisong);
        app = (ExTraceApplication) getApplication();
        user = app.getLoginUser();
        listView = (ListView) findViewById(R.id.package_paisong_lves);
        ivScan = (ImageView)findViewById(R.id.package_paisong_scan);

        mLoader = new ExpressListLoader(PackagePaisongActivity.this, PackagePaisongActivity.this);
        mLoader.LoadExpressListInPackage(user.getDelivePackageID()+"");
        setListener();
    }

    private void setListener(){
        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paiSong();
            }
        });
    }

    private void paiSong(){
        Intent intent = new Intent();
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_PAISONG);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode){
                    /*
                     * 查包
                     */
                    case REQUEST_PAISONG:
                        if (data.hasExtra("BarCode")) {			//打包
                            String id = data.getStringExtra("BarCode");
                            try {
                                deliverLoader = new DeliverLoader(PackagePaisongActivity.this);
                                deliverLoader.DispatchExpressSheet(id,user.getUID()+"");
                                mLoader = new ExpressListLoader(PackagePaisongActivity.this, PackagePaisongActivity.this);
                                mLoader.LoadExpressListInPackage(user.getDelivePackageID()+"");  //刷新
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                }
                break;
            default:
                break;
        }
    }


    @Override
    public void packDataSetChanged() {

    }

    @Override
    public void unPackDataSetChanged() {

    }

    @Override
    public List<ExpressSheet> getData() {
        return ExpressList;
    }

    @Override
    public void setData(List<ExpressSheet> data) {
        ExpressList = data;
    }

    @Override
    public void notifyDataSetChanged() {

        //Toast.makeText(PackagePaisongActivity.this, "查询"+ExpressList, Toast.LENGTH_SHORT).show();
        deliverLoader = new DeliverLoader(PackagePaisongActivity.this);
        listView.setAdapter(new PackagePaisongAdapter( PackagePaisongActivity.this,ExpressList,deliverLoader,user.getUID()+"")); //icon spair数组
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PackagePaisongActivity.this, "查询"+ExpressList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
