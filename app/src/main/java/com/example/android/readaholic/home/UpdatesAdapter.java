package com.example.android.readaholic.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.readaholic.HomeFragment;
import com.example.android.readaholic.books.BookPageActivity;

import com.example.android.readaholic.R;
import com.example.android.readaholic.profile_and_profile_settings.ProfileFragment;

import java.util.ArrayList;

public class UpdatesAdapter extends ArrayAdapter<Updates> {
    private Context activity;
    public static LinearLayout likedpost;
    public static TextView commentView;
    public UpdatesAdapter(Context context, ArrayList<Updates> objects) {
        super(context,0, objects);
        activity = context;
    }

    @Override
    /**
     * This function have one Item of Updates in position and It displays it according to the update type.
     *
     * @param position int: The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView View: The old view to reuse.we should check that this view is non-null and of an appropriate type before using.
     * @param parent ViewGroup: The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        final Updates Item = getItem(position);
        View ListItemView = convertView;
        if(ListItemView == null)
        {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.update_item,parent,false);
        }

        TextView Name = (TextView) ListItemView.findViewById(R.id.UpdatesActivity_name_textview);
        TextView date = (TextView) ListItemView.findViewById(R.id.UpdateActivity_updatedate_textview);
        TextView innerdate = (TextView) ListItemView.findViewById(R.id.UpdateActivity_updatedatelikedpost_textview);
        TextView Type = (TextView) ListItemView.findViewById(R.id.UpdatesActivity_typeofupdate_textview);
        RatingBar rateBar = (RatingBar) ListItemView.findViewById(R.id.ratingBar);
        LinearLayout viewOfBook = (LinearLayout)ListItemView.findViewById(R.id.UpdateActivity_bookview_linearlayout);
        TextView bookName = (TextView)ListItemView.findViewById(R.id.UpdatesActivity_bokkname_textview);
        TextView followerName = (TextView)ListItemView.findViewById(R.id.UpdatesActivity_followername_textview);
        TextView review = (TextView)ListItemView.findViewById(R.id.UpdatesActivity_reviewinrate_textview);
        TextView authorName = (TextView)ListItemView.findViewById(R.id.UpdateActivity_authorname_textview);
        TextView updateType = (TextView)ListItemView.findViewById(R.id.UpdatesActivity_updatetype_textview);
        TextView innerUpdatetype = (TextView)ListItemView.findViewById(R.id.UpdatesActivity_typeofupdate_textview);
        likedpost = (LinearLayout) ListItemView.findViewById(R.id.UpdatesActivity_likedpost_LinearLayout);
        final TextView likeButton = (TextView) ListItemView.findViewById(R.id.UpdatesActivity_likebutton_textveie);
        final TextView commentButton = (TextView) ListItemView.findViewById(R.id.UpdatesActivity_commentbutton_textveie);
        final Button wantToRead = (Button) ListItemView.findViewById(R.id.UpdateActivity_wanttoread_button);
         TextView numOfLike = (TextView)ListItemView.findViewById(R.id.UpdatesActivity_numberoflikes_textview);
        TextView numOfComment = (TextView)ListItemView.findViewById(R.id.UpdatesActivity_numberofcomments_textview);
        TextView userLikedPost = (TextView) ListItemView.findViewById(R.id.UpdatesActivity_namelikedpost_textview);
        TextView followerLikedPost = (TextView) ListItemView.findViewById(R.id.UpdatesActivity_followernamelikedpost_textview);
        TextView likedPostType = (TextView) ListItemView.findViewById(R.id.UpdatesActivity_typeofupdatelikedpost_textview);
        commentView = (TextView) ListItemView.findViewById(R.id.UpdatesActivity_comment_textview);
        ImageView bookImage = (ImageView) ListItemView.findViewById(R.id.UpdatesActivity_bookimage_imageview);
        Spinner wantToReadSpinner = (Spinner) ListItemView.findViewById(R.id.activitybook_sheleve_spinner);
        ImageView userimg = (ImageView) ListItemView.findViewById(R.id.UpdatesActivity_profilepicture_imageView);

        Name.setText(Item.getmNameOfUser());
        date.setText(Item.getmDateOfUpdates());
        numOfLike.setText( Integer.toString(Item.getmNumOfLikes()));
        numOfComment.setText(Integer.toString(Item.getmNumOfComments()));

        // Create an ArrayAdapter using the String array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Shelves, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        wantToReadSpinner.setAdapter(adapter);



        //Picasso.get().load("https://images.gr-assets.com/users/1489660298p2/65993249.jpg").into(bookImage);
        commentView.setVisibility(View.GONE);
        wantToRead.setBackgroundResource(R.color.colorGreen);
        wantToRead.setTextColor(Color.parseColor("#F4F1EC"));
        //update's type review or rating
        if (Item.getmTypeOfUpdates() == 0){
            if(Item.getmRatingValue() == 0){
                Type.setText("Wrote a");
                followerName.setText("Review");
                review.setText(Item.getmReview());
                followerName.setVisibility(View.VISIBLE);
                review.setVisibility(View.VISIBLE);
                rateBar.setVisibility(View.GONE);
            }else{
                Type.setText("Rated it");
                rateBar.setVisibility(View.VISIBLE);
                followerName.setVisibility(View.GONE);
                review.setVisibility(View.GONE);
                rateBar.setRating(Item.getmRatingValue());
            }
            authorName.setText(Item.getmAuthorName());
            bookName.setText(Item.getmBookName());
            viewOfBook.setVisibility(View.VISIBLE);
            likedpost.setVisibility(View.GONE);
        }
        //updates's type shelves
        else if (Item.getmTypeOfUpdates() == 1){
            Type.setText(Item.getmNameofFollow());
            bookName.setText(Item.getmBookName());
            authorName.setText(Item.getmAuthorName());
            viewOfBook.setVisibility(View.VISIBLE);
            followerName.setVisibility(View.GONE);
            likedpost.setVisibility(View.GONE);
            rateBar.setVisibility(View.GONE);
            review.setVisibility(View.GONE);
        }
        //updates's type likes or comment on update
        else if (Item.getmTypeOfUpdates() == 3 || Item.getmTypeOfUpdates() == 4){
            likedPostType.setText("Liked");
            innerdate.setText(Item.getmDateOfUpdates());
            date.setText(Item.getmInnerDate());
            followerLikedPost.setText(Item.getmNameOfUser());
            userLikedPost.setText(Item.getmNameofFollow());
            rateBar.setVisibility(View.GONE);
            review.setVisibility(View.GONE);
            viewOfBook.setVisibility(View.VISIBLE);
            switch (Item.getmInnerUpdate()){
                //Inner updates's type review or rating
                case 0:
                    followerName.setVisibility(View.GONE);
                    if(Item.getmRatingValue() == 0){
                        updateType.setText("review");
                        innerUpdatetype.setText("wrote a review");
                        review.setText(Item.getmReview());
                    }else{
                        updateType.setText("rating");
                        innerUpdatetype.setText("rated it");
                        rateBar.setRating(Item.getmRatingValue());
                        rateBar.setVisibility(View.VISIBLE);
                        review.setVisibility(View.GONE);
                    }
                    bookName.setText(Item.getmBookName());
                    authorName.setText(Item.getmAuthorName());
                    break;
                //Inner updates's type shelves(want to read ,reading , read)
                case 1:
                    updateType.setText("status update");
                    innerUpdatetype.setText("Want to read");
                    bookName.setText(Item.getmBookName());
                    authorName.setText(Item.getmAuthorName());
                    followerName.setVisibility(View.GONE);
                    break;
                //Inner updates's type following someone
                case 2:
                    updateType.setText("update");
                    innerUpdatetype.setText("is now following");
                    followerName.setText(Item.getmNameofFollow());
                    followerName.setVisibility(View.VISIBLE);
                    viewOfBook.setVisibility(View.GONE);
                    break;
            }
            //updates's type comment show comment
            if(Item.getmTypeOfUpdates() == 4){
                likedPostType.setText("Commented on");
                commentView.setText(Item.getmComment());
                commentView.setVisibility(View.VISIBLE);
            }
            likedpost.setVisibility(View.VISIBLE);
        }
        //Inner updates's type following some one
        else if (Item.getmTypeOfUpdates() == 2){
            Type.setText("is now Following");
            followerName.setText(Item.getmNameofFollow());
            followerName.setVisibility(View.VISIBLE);
            viewOfBook.setVisibility(View.GONE);
            likedpost.setVisibility(View.GONE);
            rateBar.setVisibility(View.GONE);
            review.setVisibility(View.GONE);
        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        likeButton.setOnClickListener(new View.OnClickListener() {
         /**
         * when we click on like button we change it's text
         * @param v View: The view that was clicked
         */
            public void onClick(View v) {
                if(likeButton.getText() == "Like")
                    likeButton.setText("unLike");
                else
                    likeButton.setText("unLike");

            }
        });

        Name.setOnClickListener(new View.OnClickListener(){
            /**
             * when we click on the name of the user we move to the profile fragment.
             * @param v View: The view that was clicked
             */
            public void onClick(View v){
                Fragment fragment = new ProfileFragment();
                ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                    fragment).commit();
                Bundle bundle = new Bundle();
                bundle.putInt("UserId", Item.getmUserId());
                fragment.setArguments(bundle);
            }
        });
        userimg.setOnClickListener(new View.OnClickListener(){
         /**
         * when we click on the image of the user we move to the profile fragment.
         * @param v View: The view that was clicked
         */
            public void onClick(View v){
                Fragment fragment = new ProfileFragment();
                ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                        fragment).commit();
                Bundle bundle = new Bundle();
                bundle.putInt("UserId", Item.getmUserId());
                fragment.setArguments(bundle);
            }
        });

        wantToRead.setOnClickListener(new View.OnClickListener(){
            /**
             * when we click on want to read buuton we assign this book to the user shelf
             * @param v View: The view that was clicked
             */
            public void onClick(View v){
                wantToRead.setBackgroundResource(R.color.colorPrimary);
                wantToRead.setTextColor(Color.BLACK);
            }
        });

        commentButton.setOnClickListener(new View.OnClickListener(){
            /**
             * when we click on the Comment of an update we move to the update fragment.
             * @param v View: The view that was clicked
             */
            public void onClick(View v) {
                //we already in update page if it equal 1
                if (Item.getmNewActivity() != 1) {
                    Fragment fragment = new UpdatePageFragment();
                    ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                            fragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("UpdateItem", Item);
                    fragment.setArguments(bundle);
                }
            }
        });
        bookName.setOnClickListener(new View.OnClickListener(){
            /**
             * when we click on the bookname we move to the book fragment
             * @param v View: The view that was clicked
             */
            public void onClick(View v){
               /* Fragment fragment = new MyBookFragment();
                ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                        fragment).commit();
                Bundle bundle = new Bundle();
                bundle.putParcelable("BookID", Item.getmBookId());
                fragment.setArguments(bundle);*/
               ///////////////////////////////////////////////////////add this in new fragment yo get the id
                /* Bundle bundle = this.getArguments();
                   if (bundle != null) {
                      int myInt = bundle.getInt("UserId", mUser_Id);
                    }
                */
                Intent intent = new Intent (v.getContext(), BookPageActivity.class);
                intent.putExtra("BookID",Item.getmBookId());
                v.getContext().startActivity(intent);
            }
        });
        followerName.setOnClickListener(new View.OnClickListener(){
            /**
             * when we click on the name of the follwer we move to the profile fragment.
             * @param v View: The view that was clicked
             */
            public void onClick(View v){
                if(Item.getmTypeOfUpdates() == 0 ) {
                    Fragment fragment = new UpdatePageFragment();
                    ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                            fragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("UpdateItem", Item);
                    fragment.setArguments(bundle);
                }else if(Item.getmTypeOfUpdates() == 2){
                    Fragment fragment = new ProfileFragment();
                    ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                            fragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putInt("UserId", Item.getmUserId());
                    fragment.setArguments(bundle);
                }else if(Item.getmTypeOfUpdates() == 3 || Item.getmTypeOfUpdates() == 4){
                    if(Item.getmInnerUpdate() == 0) {
                        Fragment fragment = new UpdatePageFragment();
                        ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                                fragment).commit();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("UpdateItem", Item);
                        fragment.setArguments(bundle);
                    }else if(Item.getmInnerUpdate() == 2){
                        Fragment fragment = new ProfileFragment();
                        ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                                fragment).commit();
                        Bundle bundle = new Bundle();
                        bundle.putInt("UserId", Item.getmInnerUserId());
                        fragment.setArguments(bundle);
                    }
                }


            }
        });
        followerLikedPost.setOnClickListener(new View.OnClickListener(){
            /**
             * when we click on the name of the user of the innerpost we move to the profile fragment.
             * @param v View: The view that was clicked
             */
            public void onClick(View v){
                Fragment fragment = new ProfileFragment();
                ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                        fragment).commit();
                Bundle bundle = new Bundle();
                bundle.putInt("UserId", Item.getmUserId());
                fragment.setArguments(bundle);
            }
        });
        updateType.setOnClickListener(new View.OnClickListener() {
            /**
             * when we click on the updatetype of an update we move to the update fragment.
             * @param v View: The view that was clicked
             */
            public void onClick(View v) {
                //we already in update page if it equal 1
                if (Item.getmNewActivity() != 1) {
                    Fragment fragment = new UpdatePageFragment();
                    ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.Main_fragmentLayout,
                            fragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("UpdateItem", Item);
                    fragment.setArguments(bundle);
                }
            }
        });
        if(Item.getmNewActivity() == 1){
            likedpost.setVisibility(View.GONE);
            commentView.setVisibility(View.GONE);
        }

        return ListItemView;
    }
}
