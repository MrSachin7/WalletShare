package com.sachin_himal.walletshare.ui.split;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.User;

public class AddNewGroup extends Fragment {

  private EditText editTextGroupName ;
  private FirebaseUser currentUser;
  private  String currentUserID;
  private DatabaseReference groupReference, memberReference;
  FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
private Button saveButton;

 private  Group group;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_group, container, false);
        editTextGroupName  = view.findViewById(R.id.editGroupNameText);


saveButton = view.findViewById(R.id.saveGroupName);
saveButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        SaveGroup(v);
    }
});


        group = new Group();


       currentUser = FirebaseAuth.getInstance().getCurrentUser();
       currentUserID = currentUser.getUid();

    groupReference = firebaseDatabase.getReference().child("Groups");
    memberReference = firebaseDatabase.getReference().child("Users");






    /////


       memberReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               for ( DataSnapshot dataSnapshot: snapshot.getChildren()){
               /**
                   Group group = new Group();

                   String name = (String) dataSnapshot.child("groupName").getValue();
                   group.setGroupName(name);
                   group.setAmount(0);
                   List<User> users = new ArrayList<>();
                   System.out.println( dataSnapshot.child("users").getValue());
                   group.setUsers((List<User>)  dataSnapshot.child("users").getValue());
                   System.out.println(group.getUsers().toString());
                   groupArrayList.add(group);
                   System.out.println("sucesfully added ");
                   // group.setAmount((int) dataSnapshot.child("amount").getValue());
                   cardAdapter.notifyDataSetChanged();
**/
                   System.out.println(dataSnapshot.child(currentUserID).getKey());

               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });




        ////

       return view;

    }

    public void SaveGroup(View view) {

        group.setGroupName(editTextGroupName.getText().toString());


  DatabaseReference newAddedReference = groupReference.push();
  String keyFromRecentGroup = newAddedReference.getKey();
          newAddedReference.setValue(group).addOnCompleteListener(task -> {
      if (task.isSuccessful()){
            memberReference.child(currentUserID).child("GroupList").push().setValue(keyFromRecentGroup);
          group = new Group();

         // memberReference.child(currentUserID).child("GroupList").push().setValue("")
          Toast.makeText(getContext(), "Success ", Toast.LENGTH_SHORT).show();

      }else  Toast.makeText(getContext(), "Failed ", Toast.LENGTH_SHORT).show();
    });
}}