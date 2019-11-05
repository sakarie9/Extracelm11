package extrace.ui.domain;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import extrace.loader.PathLoader;
import extrace.misc.model.CustomerInfo;
import extrace.misc.model.ExpressSheet;
import extrace.misc.model.Path;
import extrace.net.MyDataAdapter;
import extrace.ui.main.R;
import zxing.util.CaptureActivity;

public class PackageDeliverActivity extends AppCompatActivity implements MyDataAdapter<List<Path>> {
    RecyclerView recyclerView;
    List<Path> pathList;
    PathLoader pathLoader;
    ExpressSheet expressSheet;
    TextView tvReceiveSite;
    CustomerInfo customerInfo;
    public static final int REQUEST_HISTORY = 106;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_deliver);
        Intent intent = getIntent();
        //expressSheet = (ExpressSheet) intent.getSerializableExtra("expressSheet");
        //Toast.makeText(this,expressSheet+"",Toast.LENGTH_SHORT);

        recyclerView = (RecyclerView)findViewById(R.id.package_deliver_rec);
        tvReceiveSite = (TextView)findViewById(R.id.package_deliver_receivesite);


        Intent mIntent = getIntent();
        if (mIntent.hasExtra("Action")) {
            if(mIntent.getStringExtra("Action").equals("esHistory")){	//// 运输历史记录查询
                esHistory();
            }
            else{
                this.setResult(RESULT_CANCELED, mIntent);
                this.finish();
            }
        }
        else{
            this.setResult(RESULT_CANCELED, mIntent);
            this.finish();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode){
                    /*
                     * 扫码并查询历史信息
                     */
                    case REQUEST_HISTORY:

                        if (data.hasExtra("BarCode")) {
                            String id = data.getStringExtra("BarCode");
                            try {
                                pathLoader = new PathLoader(PackageDeliverActivity.this, PackageDeliverActivity.this);
                                String esId = new String("");
                                pathLoader.getPath(id);
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
    public List<Path> getData() {
        return pathList;
    }

    @Override
    public void setData(List<Path> data) {
        pathList = data;
    }

    @Override
    public void notifyDataSetChanged() {

        //Toast.makeText(PackageDeliverActivity.this,pathList+"",Toast.LENGTH_SHORT).show();
        customerInfo = pathList.get(0).getReceiver();
        tvReceiveSite.setText("[收货地址]"+customerInfo.getRegionString()+customerInfo.getAddress()+customerInfo.getDepartment());
        PackageDeliverAdapter logisticsAdapter = new PackageDeliverAdapter(this, pathList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(logisticsAdapter);

    }

    private void esHistory(){
        Intent intent = new Intent();
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_HISTORY);
    }


}
