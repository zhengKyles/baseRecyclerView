# baseRecyclerView
baseRecyclerView

一个封装的recyclerview库，基于recyclerview和BaseQuickAdapter和databinding

#使用方法

implementation 'com.kyle:baseRecyclerView:1.0.3'

```java
<com.kyle.baserecyclerview.LRecyclerView
                android:id="@+id/list"
                app:direction="vertical"   //recyclerview方向  可选项vertical竖直  horizontal水平   grid网格
                app:divider_height_vertical="10dp"  //竖直分割线高度
                app:divider_width_horizontal="10dp" //水平分割线宽度
                app:recycler_divider="#333333"      //分割线样式  颜色或者图片
                app:lastEnable="true"               //最后一行、列是否绘制分割线
                app:span_count="3"                  //当grid时生效->列数
                android:layout_width="match_parent"
                android:layout_height="match_parent" />
```
```java
LRecyclerView list=findViewById(R.id.list);
list.setAdapter(继承com.kyle.baserecyclerview.BaseAdapter的adapter);
```
然后给adapter设置数据就行

ps:本库使用databinding，不使用databinding的用不了
