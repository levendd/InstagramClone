package com.levojuk.instagramclone.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.levojuk.instagramclone.R;
import com.levojuk.instagramclone.adapter.PostAdapter;
import com.levojuk.instagramclone.databinding.ActivityFeedBinding;
import com.levojuk.instagramclone.model.Post;
import com.levojuk.instagramclone.model.Users;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Post> postArrayList;
    ArrayList<Users> userArrayList;
    private ActivityFeedBinding binding;
    PostAdapter postAdapter;
    ServerTimestamp timeStamp;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        postArrayList = new ArrayList<>();
        userArrayList = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getData();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList);
        binding.recyclerView.setAdapter(postAdapter);

    }
    private  void getData(){

        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(FeedActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                if (value != null){
                   for (DocumentSnapshot snapshot : value.getDocuments()){
                       Map<String ,Object> data = snapshot.getData();
                       String useremail = (String) data.get("useremail");
                       username = (String) data.get("username");
                       String comment = (String) data.get("comment");
                       String downloadUrl = (String) data.get("downloadUrl");
                       Timestamp timestamp =  (Timestamp)   data.get("date");
                       Post post = new Post(useremail,username,comment,downloadUrl,setTime(timestamp));
                       postArrayList.add(post);
                   }
                   postAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    String setTime(Timestamp timestamp){
        if (timestamp!=null){Date datetime =new Date(timestamp.toDate().getTime());
            DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
            String date=df.format(datetime);
            return date;}
        else {

            return "";
        }

}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_post){
            Intent intent = new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intent);

        }
        else if(item.getItemId() == R.id.log_out) {
            auth.signOut();
            Intent intentToMain = new Intent(FeedActivity.this,MainActivity.class);
            startActivity(intentToMain);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.exit_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
