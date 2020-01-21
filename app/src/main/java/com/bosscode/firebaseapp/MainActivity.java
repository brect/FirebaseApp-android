package com.bosscode.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference referencia;
    private FirebaseAuth mAuth;

    private TextView textoTela;

    String usuario = "xurupita@teste.com";
    String pw = "1234abc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        referencia = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        textoTela = findViewById(R.id.textoTela);


        /*
        //PUSH CRIA UM ID UNICO AUTOMATICAMENTE
        DatabaseReference usuarios = referencia.child("usuarios");

        Usuario usuario = new Usuario();
        usuario.setNome("Samara");
        usuario.setSobrenome("Ogata");
        usuario.setIdade(33);


        usuarios.push().setValue(usuario);
        */


        /*Cadastro de usuario via FIREBASE
        mAuth.createUserWithEmailAndPassword("xurupita@teste.com", "1234abc")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.i("CreateUser", "onComplete: Sucesso ao cadastrar o usuario");

                        }else {
                            Log.i("CreateUser", "onComplete: Erro ao cadastrar o usuario");
                        }
                    }
        });*/

        /*Logar usuario no app
        mAuth.signInWithEmailAndPassword(usuario, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (mAuth.getCurrentUser() != null){
                    Log.i("singIn", "Sucesso ao logar usuario!");

                }else {
                    Log.i("singIn", "Erro ao efetuar o login");
                }
            }
        });*/


        /*Deslogar usuario do app
        mAuth.signOut();

        Verifica se usuario esta logado no app
        if (mAuth.getCurrentUser() != null){
            Log.i("UserStatus", "onComplete: Usuario está logado");

        }else {
            Log.i("UserStatus", "onComplete: Usuario não está logado");
        }*/




        /*

        //RECUPERA DADOS
        referencia.child("usuarios").child("0001").child("nome").setValue("Xurupita da Silva");
        DatabaseReference usuarios = referencia.child("usuarios");
        DatabaseReference produtos = referencia.child("produtos");


        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.i("FIREBASE", "onDataChange: " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */

        //SALVAR DADOS NO FIREBASE
        /*Usuario usuario = new Usuario();
        usuario.setNome("Samara");
        usuario.setSobrenome("Ogata");
        usuario.setIdade(33);
        usuarios.child("002").setValue(usuario);

        Produto produto = new Produto();
        produto.setMarca("Samsung");
        produto.setTipoProduto("Celular");
        produto.setDescricao("S10+");
        produto.setPreco(2999.00);
        produtos.child("001").setValue(produto);*/

    }


}
