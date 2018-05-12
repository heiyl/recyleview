# recyleview
material design 系列 recyleview
概述

RecylerView控件从Android 5.0开始谷歌公司推出的新控件用于替代ListView、GridView的控件。不是已经有ListView了吗，为什么还要RecyclerView呢？下面我们看下recyleview都有哪些特点。

recyleview主要特点：
1、RecyclerView提供了一种插拔式的体验，高度的解耦，异常的灵活；
2、自带了性能优化(ViewHolder复用机制)；
3、低耦合高内聚。（通过设置它提供的不同LayoutManager，ItemDecoration , ItemAnimator实现不同视图效果。）

recyleview缺点：（同listveiw对比）
1、RecyclerView没有条目点击、长按事件，需要自己封装。
2、RecyclerView没有默认的分割线，需要自己绘制。
3、RecyclerView 没有添加头部和底部视图的功能

recyleview主要功能：
我们会从如下几个功能方面实现recyleview主要功能
1、通过布局管理器LayoutManager控制你想要其显示视图样式的方式；
2、通过ItemDecoration控制Item间的间隔线的展示（需要自定义绘制）；
3、通过ItemAnimator控制Item增删的动画
4、通过自己封装RecyclerView条目点击、长按事件；
5、RecyclerView 添加头部和底部（需要自己实现，recyleview自身没有此功能）
6、RecyclerView 功能延展

基本用法

RecyclerView 使用时的基本代码块：
相对于ListView的代码，ListView可能只需要去设置一个adapter就能正常使用了。而RecyclerView基本需要下面好多的步骤，那么为什么会添加这么多的步骤呢？其高度的解耦，给予你充分的定制自由（所以你才可以轻松的通过这个控件实现ListView,GirdView，瀑布流等效果）。

recyleview = this.findViewById(R.id.recyleview);
//设置布局管理器
recyleview.setLayoutManager(linearLayoutManager);
//添加分割线
recyleview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, R.drawable.line_diver_gray));
// //设置Item增加、移除动画
recyleview.setItemAnimator(new DefaultItemAnimator());
//设置adapter
adapter = new RVElementaryAdapter(data);
recyleview.setAdapter(adapter);
//设置条目的点击事件
adapter.setOnItemClickListener(this);
//设置条目的长按事件
adapter.setOnItemLongClickListener(this);

RecyclerView的Adapter的写法：
在了解了RecyclerView的一些控制之后，紧接着来看看它的Adapter的写法，RecyclerView的Adapter与ListView的Adapter还是有点区别的，RecyclerView.Adapter，需要实现3个方法： 
1、 onCreateViewHolder() 
这个方法主要生成为每个Item inflater出一个View，但是该方法返回的是一个ViewHolder。该方法把View直接封装在ViewHolder中，然后我们面向的是ViewHolder这个实例，当然这个ViewHolder需要我们自己去编写。直接省去了当初的convertView.setTag(holder)和convertView.getTag()这些繁琐的步骤。
2、 onBindViewHolder() 
这个方法主要用于适配渲染数据到View中。方法提供给你了一viewHolder而不是原来的convertView。
3、 getItemCount() 
这个方法就类似于BaseAdapter的getCount方法了，即总共有多少个条目。

如下是recyleview的adapter基本代码块：

public class RVElementaryAdapter extends RecyclerView.Adapter<RVElementaryAdapter.ElementaryViewHolder> {
    @Override
    public ElementaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建ViewHolder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent,false);
        ElementaryViewHolder elementaryViewHolder = new ElementaryViewHolder(itemView);
        return elementaryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVElementaryAdapter.ElementaryViewHolder holder, int position) {
        //绑定数据
        holder.tv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null? 0 :data.size();
    }
}

实现的列表效果图如下：


RecyclerView 控制布局样式LayoutManager：

上面实现了类似ListView样子的Demo，通过使用其默认的LinearLayoutManager。RecyclerView.LayoutManage这是一个抽象类，系统提供了如下3个实现类：
1、LinearLayoutManager 现行管理器，支持横向、纵向。
2、GridLayoutManager 网格布局管理器
3、StaggeredGridLayoutManager 瀑布就式布局管理器

下面实现以下各种视图间的转化效果：
代码主要是通过recyleview.setLayoutManager(linearLayoutManager);设置不同的layoutManager来展示不同的视图样式，如下关键的代码快：

/**
 *切换布局效果
 */
private void changeView(){
    adapter.setViewType(currentViewType);
    recyleview.removeItemDecoration(dividerItemDecoration);
    recyleview.removeItemDecoration(dividerGridViewItemDecoration);
    switch (currentViewType){
        case Constnats.VIEW_TYPE_LISTVIEW:
            recyleview.addItemDecoration(dividerItemDecoration);
            recyleview.setLayoutManager(linearLayoutManager);
            break;
        case Constnats.VIEW_TYPE_GRIDVIEW:
            recyleview.addItemDecoration(dividerGridViewItemDecoration);
            recyleview.setLayoutManager(gridLayoutManager);
            break;
        case Constnats.VIEW_TYPE_HORIZONTALGRIDVEW:
            recyleview.addItemDecoration(dividerGridViewItemDecoration);
            recyleview.setLayoutManager(horizontalGridLayoutManager);
            break;
        case Constnats.VIEW_TYPE_STAGGEREDGRIDVIEW:
            recyleview.addItemDecoration(dividerGridViewItemDecoration);
            recyleview.setLayoutManager(staggeredGridLayoutManager);
            break;
    }
}

效果图如下：






RecyclerView添加条目、删除条目功能，并添加条目动画：
注意的点：
1、RecyclerView.Adapter中添加删除条目需要刷新视图，平时我们刷新的方法调用notifyDataSetChanged();
--notifyDataSetChanged();列表全局刷新，给recyleview添加增删条目的动画时，调用这种刷新方法时无法生效的
2、RecyclerView.Adapter中为我们提供了很多自带酷炫的增加删除动画，包括局部刷新的方法。
--notifyItemInserted(int position): 列表position位置添加一条数据时可以调用，伴有动画效果
--notifyItemRemoved(int position) :列表position位置移除一条数据时调用，伴有动画效果
--notifyItemMoved(int fromPosition, int toPosition) 列表fromPosition位置的数据移到toPosition位置时调用，伴有动画效果
--notifyItemRangeChanged(int positionStart, int itemCount) 列表从positionStart位置到itemCount数量的列表项进行数据刷新
--notifyItemRangeInserted(int positionStart, int itemCount) 列表从positionStart位置到itemCount数量的列表项批量添加数据时调用，伴有动画效果
·notifyItemRangeRemoved(int positionStart, int itemCount) 列表从positionStart位置到itemCount数量的列表项批量删除数据时调用，伴有动画效果
3、实际增删操作过程中position不会自动增加，导致数据错位的问题，所以，当我们需要使用这些特效方法的时候，必须要重新刷新一遍数据，纠正position。
4、设置Item增加、移除动画recyleview.setItemAnimator(new DefaultItemAnimator());
在adapter中实现添加和删除条目的功能，避免了数据错位的问题，功能代码如下：
/**
     * 添加条目
     * @param position
     */
    public void addData(int position){
        data.add(position,"additem"+position);
        heights.add(position,(int) Math.max(200,Math.random()*600));
        notifyItemInserted(position);

        // 加入如下代码保证position的位置正确性
        if (position != data.size() - 1) {
            //列表从positionStart位置到itemCount数量的列表项进行数据刷新
            notifyItemRangeChanged(position, data.size() - position);
        }
//    notifyDataSetChanged();
    }

    /**
     * 移除条目
     * @param position
     */
    public void removeData(int position){
        data.remove(position);
        heights.remove(position);
        notifyItemRemoved(position);

        // 加入如下代码保证position的位置正确性
        if (position != data.size() - 1) {
            //列表从positionStart位置到itemCount数量的列表项进行数据刷新
            notifyItemRangeChanged(position, data.size() - position);
        }
//        notifyDataSetChanged();
    }

RecyleView添加点击事件和长按事件Click and LongClick：
上面说到RecyleView的缺点系统没有提供ClickListener和LongClickListener，我们也可以自己去添加，需要多实现些代码。 可以通过adapter中自己去提供回调自己去实现。adapter.setOnItemClickListener(this)设置条目的点击事件；adapter.setOnItemLongClickListener(this)设置条目的长按事件，
注意：解决点击position可能错位的问题，通过构造点击事件把position传给自己实现的点击事件，以便于记录点击确定的position
关键代码块如下：

@Override
public void onBindViewHolder(@NonNull RVElementaryAdapter.ElementaryViewHolder holder, int position) {
    if(itemClickListener != null) {
        holder.tv.setOnClickListener(new MyClickListener(position));
    }
    if(itemLongClickListener != null) {
        holder.tv.setOnLongClickListener(new MyLongClickListener(position));
    }
}

private ItemClickListener itemClickListener;
private ItemClickListener itemLongClickListener;
/**
 * 设置点击事件
 */
public void setOnItemClickListener(ItemClickListener itemClickListener){
    this.itemClickListener = itemClickListener;
}
/**
 * 设置长按点击事件
 */
public void setOnItemLongClickListener(ItemClickListener itemLongClickListener){
    this.itemLongClickListener = itemLongClickListener;
}
public interface ItemClickListener{
    void onItemClick(int position);
    void onItemLongClick(int position);
}
/**
 * 条目的点击事件
 */
public class MyClickListener implements View.OnClickListener{
    int position;
    MyClickListener(int position){
        this.position = position;
    }
    @Override
    public void onClick(View v) {
        itemClickListener.onItemClick(position);
    }
}

/**
 * 条目的长按事件
 */
public class MyLongClickListener implements View.OnLongClickListener{
    int position;
    MyLongClickListener(int position){
        this.position = position;
    }

    @Override
    public boolean onLongClick(View v) {
        itemLongClickListener.onItemLongClick(position);
        return true;
    }
}
到此基本介绍了RecylerView常见用法，包含了：
1、系统提供了几种LayoutManager的使用；
2、如何使用ItemAnimator为RecylerView去添加Item移除、添加以及动画效果。
3、介绍了如何添加ItemClickListener与ItemLongClickListener。
4、recyleview的adapter的用法
接下来会看一下RecyleView添加分割线的功能。

源码demo链接：
https://github.com/heiyl/recyleview
