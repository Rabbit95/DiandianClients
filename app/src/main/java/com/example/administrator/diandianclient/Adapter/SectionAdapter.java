package com.example.administrator.diandianclient.Adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.diandianclient.data.MySection;
import com.example.administrator.diandianclient.R;
import com.example.administrator.diandianclient.data.Video;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }
    @Override
    protected void convertHead(BaseViewHolder helper, final MySection item) {
        helper.setText(R.id.header, item.header);
        helper.setVisible(R.id.more, item.isMore());
        helper.addOnClickListener(R.id.more);
    }


    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        Video video = (Video) item.t;
        switch (helper.getLayoutPosition() % 2) {
            case 0:
                Glide.with(mContext).load(video.getImageURL()).into((ImageView) helper.getView(R.id.iv));
                break;
            case 1:
                Glide.with(mContext).load(video.getImageURL()).into((ImageView) helper.getView(R.id.iv));
                break;

        }
        helper.setText(R.id.tv, video.getName()+"\t\t"+video.getPrice()+"元");
    }
}
