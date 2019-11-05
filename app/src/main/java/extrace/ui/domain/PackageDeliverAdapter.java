package extrace.ui.domain;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import extrace.misc.model.ExpressSheet;
import extrace.misc.model.Path;
import extrace.misc.model.UserInfo;
import extrace.ui.main.R;
import extrace.ui.util.DensityUtil;

public class PackageDeliverAdapter extends RecyclerView.Adapter<PackageDeliverAdapter.LogisticsAdapterHolder> {

    private Context context;
    private LayoutInflater mLayoutInflater;
    List<Path> itemList;

    public PackageDeliverAdapter(Context context,  List<Path> itemList) {
        this.context = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.itemList = itemList;
        Toast.makeText(context,itemList+"",Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public PackageDeliverAdapter.LogisticsAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PackageDeliverAdapter.LogisticsAdapterHolder(mLayoutInflater.inflate(R.layout.logistics_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PackageDeliverAdapter.LogisticsAdapterHolder holder, int position) {

        try {
            holder.tv_location.setText(itemList.get(position).getNodeName()+"");//订单状态

            SimpleDateFormat myFmt=new SimpleDateFormat("MM月dd日 hh:mm");
            holder.tv_time.setText(myFmt.format(itemList.get(position).getStart()));// 格式化时间
          //Toast.makeText(context,itemList+"",Toast.LENGTH_SHORT).show();
           switch (itemList.get(position).getStatus()){
               case 0:
                   holder.tv_status.setText("等待揽收");
                   break;
               case 1:
                   String[] userInfo = itemList.get(position).getUserInfo().split(" "); //用split()函数直接分割
                   String tel = userInfo[2].substring(3);
                   holder.tv_status.setText("已揽收"+"["+"揽收人："+userInfo[1]+"，电话："+ tel+"]");
                   break;
               case 2:
                   holder.tv_status.setText("已分拣");
                   break;
               case 3:
                   holder.tv_status.setText("已发出");
                   break;
               case 4:
                   holder.tv_status.setText("派送中");
                   break;
               case 5:
                   userInfo = itemList.get(position).getUserInfo().split(" "); //用split()函数直接分割
                   tel = userInfo[2].substring(3);
                   holder.tv_status.setText("已揽收"+"["+"揽收人："+userInfo[1]+"，电话："+ tel+"]");
                   break;
           }



            if (position == 0) {
                //红色的圆点
               // holder.iv_status.setImageResource(R.drawable.logistics_shape_circle_red);
                RelativeLayout.LayoutParams pointParams = new RelativeLayout.LayoutParams(DensityUtil.dp2px(context, 35), DensityUtil.dp2px(context, 35));//大小
                pointParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//                holder.iv_status.setLayoutParams(pointParams);
//                holder.iv_status.setBackgroundResource(R.drawable.up);
                holder.iv_status.setImageResource(R.drawable.up);
                holder.iv_status.setLayoutParams(pointParams);



                holder.tv_time.setTextColor(context.getResources().getColor(R.color.myblue));
                holder.tv_status.setTextColor(context.getResources().getColor(R.color.myblue));
                holder.tv_location.setTextColor(context.getResources().getColor(R.color.myblue));

                //灰色的竖线
                RelativeLayout.LayoutParams lineParams = new RelativeLayout.LayoutParams(DensityUtil.dp2px(context, 1), ViewGroup.LayoutParams.MATCH_PARENT);
                lineParams.addRule(RelativeLayout.BELOW, R.id.iv_status);//让直线置于圆点下面
                lineParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                holder.iv_line.setLayoutParams(lineParams);

            } else {
//                holder.iv_status.setBackgroundResource(R.mipmap.ic_logistics_bottom);
                holder.iv_status.setImageResource(R.drawable.logistics_shape_circle_gray);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(DensityUtil.dp2px(context, 10), DensityUtil.dp2px(context, 10));
                lp.addRule(RelativeLayout.CENTER_IN_PARENT);

                holder.iv_status.setLayoutParams(lp);

                holder.tv_time.setTextColor(context.getResources().getColor(R.color.gray));
                holder.tv_status.setTextColor(context.getResources().getColor(R.color.gray));

                //灰色的竖线
                RelativeLayout.LayoutParams lineParams = new RelativeLayout.LayoutParams(DensityUtil.dp2px(context, 1), ViewGroup.LayoutParams.MATCH_PARENT);
//                lineParams.addRule(RelativeLayout.BELOW, R.id.iv_status);//让直线置于圆点下面
                lineParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                holder.iv_line.setLayoutParams(lineParams);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class LogisticsAdapterHolder extends RecyclerView.ViewHolder {

        ImageView iv_status;
        TextView tv_status;
        TextView tv_location;
        TextView tv_time;
        ImageView iv_line;

        LogisticsAdapterHolder(View view) {
            super(view);
            iv_line = (ImageView) view.findViewById(R.id.iv_line);
            iv_status = (ImageView) view.findViewById(R.id.iv_status);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_location = (TextView) view.findViewById(R.id.tv_location);
            tv_time = (TextView) view.findViewById(R.id.tv_time);

        }
    }
}
