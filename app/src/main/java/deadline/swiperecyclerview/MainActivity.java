package deadline.swiperecyclerview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.List;

import deadline.swiperecyclerview.footerView.Item;
import deadline.swiperecyclerview.footerView.SimpleFooterView;

public class MainActivity extends AppCompatActivity {
    //上拉下拉加载组件
    private SwipeRecyclerView recyclerView;
    //数据
    private List<Item> data;
    //比listView强大
    private RecyclerViewAdapter adapter;
    //每一页的大小
    private int pagerSize = 10;
    //当前页
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载主要布局layout
        setContentView(R.layout.demo_activity_main);

        recyclerView = (SwipeRecyclerView) findViewById(R.id.swipeRecyclerView);

        //set color
        recyclerView.getSwipeRefreshLayout()
                .setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        //set layoutManager
        recyclerView.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.getRecyclerView().setLayoutManager(new GridLayoutManager(this, 3));
        //recyclerView.getRecyclerView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        //禁止下拉刷新
       // recyclerView.setRefreshEnable(false);

        //禁止加载更多
        //recyclerView.setLoadMoreEnable(false);

        //设置emptyView
        TextView textView = new TextView(this);
        textView.setText("no data!");
        recyclerView.setEmptyView(textView);

        //设置footerView
         recyclerView.setFooterView(new SimpleFooterView(this));

        //由于SwipeRecyclerView中对GridLayoutManager的SpanSizeLookup做了处理，因此对于使用了
        //GridLayoutManager又要使用SpanSizeLookup的情况，可以这样使用！
        /*recyclerView.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 3;
            }
        });*/

        //设置去除footerView 的分割线
        recyclerView.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);

                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(0xEEEECCCC);

                Rect rect = new Rect();
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                final int childCount = parent.getChildCount() - 1;
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);

                    //获得child的布局信息
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    final int top = child.getBottom() + params.bottomMargin;
                    final int itemDividerHeight = 1;//px
                    rect.set(left + 50, top, right - 50, top + itemDividerHeight);
                    c.drawRect(rect, paint);
                }
            }
        });

        //设置noMore
        recyclerView.onNoMore(this.getString(R.string.app_nhmd));

        //设置网络处理
        recyclerView.onNetChange(true);

        //设置错误信息
        recyclerView.onError(this.getString(R.string.app_thae));

        //数据的list
        data = new ArrayList<>();
        //adapter初始化
        adapter = new RecyclerViewAdapter();
        //设置adapter
        recyclerView.setAdapter(adapter);

        recyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            /**
             * 下拉刷新
             */
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //先清空data数据list
                        data.clear();
                        //放置pagerSize的数量个，实际中就是http请求回来的数据,请求时候根据currentPage,pagerSize等
                        {
                            data.add(new Item(MainActivity.this.getString(R.string.app_apple), R.drawable.apple_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_banana), R.drawable.banana_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_orange), R.drawable.orange_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_watermelon), R.drawable.watermelon_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_pear), R.drawable.pear_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_grape), R.drawable.grape_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_pineapple), R.drawable.pineapple_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_strawberry), R.drawable.strawberry_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_cherry), R.drawable.cherry_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_mango), R.drawable.mango_pic));
                        }
                        //触发完成的函数
                        recyclerView.complete();
                        //数据list通知data数据改变
                        adapter.notifyDataSetChanged();

                    }
                }, 1000);//延迟1秒执行，模拟http请求数据回来

            }

            /**
             * 上拉加载更多
             */
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //实际中就是http请求回来的数据,请求时候根据currentPage,pagerSize等追加数据
                        {
                            data.add(new Item(MainActivity.this.getString(R.string.app_apple), R.drawable.apple_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_banana), R.drawable.banana_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_orange), R.drawable.orange_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_watermelon), R.drawable.watermelon_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_pear), R.drawable.pear_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_grape), R.drawable.grape_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_pineapple), R.drawable.pineapple_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_strawberry), R.drawable.strawberry_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_cherry), R.drawable.cherry_pic));
                            data.add(new Item(MainActivity.this.getString(R.string.app_mango), R.drawable.mango_pic));
                        }
                        //请求回来currentPage加1
                        currentPage++;
                        //模拟没有更多数据情况
                        if(data.size() > 20){
                            recyclerView.onNoMore(MainActivity.this.getString(R.string.app_nhmd));
                        }else {
                            recyclerView.stopLoadingMore();
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, 1000);//延迟1秒执行，模拟http请求数据回来
            }
        });

        //设置自动下拉刷新，切记要在recyclerView.setOnLoadListener()之后调用
        //因为在没有设置监听接口的情况下，setRefreshing(true),调用不到OnLoadListener
        recyclerView.setRefreshing(true);
    }

    //自定义的适配器
    private class RecyclerViewAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        /**
         * 获取数据的总数量
         * @return
         */
        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        /**
         * 创建ItemViewHolder
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.demo_layout_item, parent, false);

            return new ItemViewHolder(view);
        }

        /**
         * 绑定事件
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(final ItemViewHolder holder, final int position) {
            //绑定文本数据
            holder.tv.setText(data.get(position).getName());
            holder.iv.setImageResource(data.get(position).getImageId());

            //for test item click listener 设置文本上面的事件点击
            holder.tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "i am Num " + position, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    /**
     * holder
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        View viewItem;
        TextView tv;
        ImageView iv;

        public ItemViewHolder(View view) {
            super(view);
            viewItem =view;
            tv = (TextView) view.findViewById(R.id.tv);
            iv = (ImageView) view.findViewById(R.id.img);
        }
    }
}