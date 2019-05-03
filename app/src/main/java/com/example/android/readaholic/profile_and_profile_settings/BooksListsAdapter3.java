package com.example.android.readaholic.profile_and_profile_settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.readaholic.R;
import com.example.android.readaholic.explore.BookModel;
import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * BookListsAdapter of Currently Reading Books
 * @author Hossam Ahmed
 */
public class BooksListsAdapter3 extends RecyclerView.Adapter<BooksListsAdapter3.MyViewHolder> {
    private List<BookModel> mBooks;
    private Context mcontext;
    private customItemCLickLisenter customItemCLickLisenter;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    /**
     * MyViewHolder class to hold the view elements
     * @author Hossam Ahmed
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        //@BindView(R.id.CurrentlyReadingList_CurrentlyReadingBook_ImageView)
        private ImageView UserImageView;
        ViewGroup viewGroup;
        /**
         * view holder constructor
         * @param v view
         */
        public MyViewHolder(View v) {
            super(v);
          //  ButterKnife.bind(v);
            UserImageView= (ImageView)v.findViewById(R.id.CurrentlyReadingList_CurrentlyReadingBook_ImageView);
            viewGroup = (ViewGroup)itemView;
        }


        @Override
        public void onClick(View v) {

        }
    }
    /**
     * Adpater constructor
     * @param context context of the layout
     * @param books user object to fill the layout with their data
     */
    // Provide a suitable constructor (depends on the kind of dataset)
    public BooksListsAdapter3(Context context, List<BookModel> books, customItemCLickLisenter Listener) {
        mBooks=books;
        mcontext = context;
        this.customItemCLickLisenter = Listener;
    }
    /**
     * onCreateViewHolder to inflate the layout
     * @param parent parent view
     * @param viewType type of the view
     * @return MyViewHolder object
     */
    // Create new views (invoked by the layout manager)
    @Override
    public BooksListsAdapter3.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v =  LayoutInflater.from(mcontext).inflate(R.layout.currentlyreadinglist, parent, false);
        final BooksListsAdapter3.MyViewHolder mViewHolder = new BooksListsAdapter3.MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customItemCLickLisenter.onItemClick(v,mViewHolder.getPosition());
            }
        });

        return mViewHolder;

    }

    // Replace the contents of a view (invoked by the layout manager)
    /**
     * OnBindViewHolder to Replace the contents of a view (invoked by the layout manager)
     * @param holder holding the view
     * @param position position of the current view.
     */
    @Override
    public void onBindViewHolder(BooksListsAdapter3.MyViewHolder holder, int position) {
        // - get element from my dataset at this position
        // - replace the contents of the view with that element
        holder.viewGroup.removeAllViews();

      Picasso.get().load(mBooks.get(position).getmImageUrl()).into(holder.UserImageView);
        //((MyViewHolder)holder).UserImageView.setImageResource(R.drawable.reader);
        holder.viewGroup.addView(holder.UserImageView);
    }
    /**
     * getItemsCount to get the list items number.
     * @return the size of users list
     */
    @Override
    public int getItemCount() {
        return mBooks.size();
    }
    public interface customItemCLickLisenter
    {
        public void onItemClick(View v,int position);
    }

}

