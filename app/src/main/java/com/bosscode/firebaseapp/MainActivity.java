package com.bosscode.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference referencia;
    private FirebaseAuth mAuth;

    private StorageReference storageReference;

    private TextView textoTela;

    String usuario = "xurupita@teste.com";
    String pw = "1234abc";


    private ImageView imageFoto;
    private Button btnUpload;
    private Button btnDelete;
    private Button btnBaixar;

    private ImageView imagemPadrao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        referencia = FirebaseDatabase.getInstance().getReference();
//        mAuth = FirebaseAuth.getInstance();




//            *CONFIGURA UPLOAD DE IMAGEM
//
        imageFoto = findViewById(R.id.imageViewParaUpload);
        btnUpload = findViewById(R.id.buttonUpload);
        btnDelete = findViewById(R.id.buttonDelete);
        btnBaixar = findViewById(R.id.buttonDownload);




        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            Configura para imagem ser salva em memória
                imageFoto.setDrawingCacheEnabled(true);
                imageFoto.buildDrawingCache();

                //Recupera bitmap da imagem (image a ser carregada)
                Bitmap bitmap = imageFoto.getDrawingCache();

                //Comprimo bitmap para um formato png/jpeg
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos );

                //converte o baos para pixel brutos em uma matriz de bytes
                // (dados da imagem)
                byte[] dadosImagem = baos.toByteArray();

                //Define nós para o storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference imagens = storageReference.child("imagens");

                //NOme da imagem
//                String nomeArquivo = UUID.randomUUID().toString();
                String nomeArquivo = "xurupita";
                final StorageReference imagemRef = imagens.child( nomeArquivo + ".jpeg");

                //Retorna objeto que irá controlar o upload
                UploadTask uploadTask = imagemRef.putBytes( dadosImagem );

                uploadTask.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MainActivity.this,
                                "Upload da imagem falhou: " + e.getMessage().toString(),
                                Toast.LENGTH_LONG ).show();

                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Uri url = taskSnapshot.getUploadSessionUri();

                        imageFoto.setImageResource(R.drawable.padrao);

                        Toast.makeText(MainActivity.this,
                                "Sucesso ao fazer upload de: " + imagemRef ,
                                Toast.LENGTH_LONG ).show();

                    }
                });

            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageFoto.setDrawingCacheEnabled(true);
                imageFoto.buildDrawingCache();

                //Recupera bitmap da imagem (image a ser carregada)
                Bitmap bitmap = imageFoto.getDrawingCache();

                //Comprimo bitmap para um formato png/jpeg
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos );

                //converte o baos para pixel brutos em uma matriz de bytes
                // (dados da imagem)
                byte[] dadosImagem = baos.toByteArray();


                //Define nós para o storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference imagens = storageReference.child("imagens");

                //NOme da imagem
//                String nomeArquivo = UUID.randomUUID().toString();
                String nomeArquivo = "xurupita";
                StorageReference imagemRef = imagens.child( nomeArquivo + ".jpeg");

                //DELETA IMAGEM DO FIREBASE
                imagemRef.delete().addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MainActivity.this,
                                "Erro ao deletar arquivo",
                                Toast.LENGTH_LONG ).show();

                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(MainActivity.this,
                                "Sucesso ao deletar arquivo",
                                Toast.LENGTH_LONG ).show();


                        imageFoto.setImageResource(R.drawable.padrao);
                    }
                });

            }
        });


        btnBaixar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //Configura para imagem ser salva em memória
                imageFoto.setDrawingCacheEnabled(true);
                imageFoto.buildDrawingCache();

                //Recupera bitmap da imagem (image a ser carregada)
                Bitmap bitmap = imageFoto.getDrawingCache();

                //Comprimo bitmap para um formato png/jpeg
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos );

                //converte o baos para pixel brutos em uma matriz de bytes
                // (dados da imagem)
                byte[] dadosImagem = baos.toByteArray();

                //Define nós para o storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference imagens = storageReference.child("imagens");
                StorageReference imagemRef = imagens.child("xurupita.jpeg");

                Glide.with(MainActivity.this)
                        .using(new FirebaseImageLoader())
                        .load( imagemRef )
                        .into( imageFoto );

            }



        });

    }



//
//        /*
//        DatabaseReference usuarios = referencia.child("usuarios");
//
//         */
//        /*
//        //PESQUISA/FILTRO USUARIOS NO FIREBASE
//         */
//
////        DatabaseReference usuarioPesquisa = usuarios.child("-LzE9mn6QVpJpKm59vIA");
////        Query usuarioPesquisa = usuarios.orderByChild("nome").equalTo("Bruno");
////        Query usuarioPesquisa = usuarios.orderByKey().limitToFirst(2);
////        Query usuarioPesquisa = usuarios.orderByKey().limitToLast(2);
//
//        /*MAIOR OU IGUAL (>=)*/
////        Query usuarioPesquisa = usuarios.orderByChild("idade").startAt(30);
//
//        /*MENOR OU IGUAL (<=)*/
////        Query usuarioPesquisa = usuarios.orderByChild("idade").endAt(30);
//
//        /*FILTRAR ENTRE DOIS VALORES*/
////        Query usuarioPesquisa = usuarios.orderByChild("idade")
////                                        .startAt(18)
////                                        .endAt(33);
//
//        /*
////        FILTRAR ENTRE PALAVRAS
//
//        Query usuarioPesquisa = usuarios.orderByChild("sobrenome")
//                                        .startAt("L")
//                                        .endAt("L" + "\uf8ff");
//
//        usuarioPesquisa.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
////                Usuario dadosUsuario = dataSnapshot.getValue(Usuario.class);
////                Log.i("Dados usuario ", "nome: " + dadosUsuario.getNome() + " Idade: " + dadosUsuario.getNome());
//                Log.i("Dados usuario ", dataSnapshot.getValue().toString() );
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//*/
//
//
//        /*
//        //PUSH CRIA UM ID UNICO AUTOMATICAMENTE
//
//        Usuario usuario = new Usuario();
//        usuario.setNome("Bruno");
//        usuario.setSobrenome("Lima");
//        usuario.setIdade(26);
//
//
//        Usuario usuario2 = new Usuario();
//        usuario2.setNome("Samara");
//        usuario2.setSobrenome("Ogata");
//        usuario2.setIdade(33);
//
//        //PUSH CRIA ID
//        usuarios.push().setValue(usuario);
//        usuarios.push().setValue(usuario2);
//        */
//
//        /*Cadastro de usuario via FIREBASE
//        mAuth.createUserWithEmailAndPassword("xurupita@teste.com", "1234abc")
//                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            Log.i("CreateUser", "onComplete: Sucesso ao cadastrar o usuario");
//
//                        }else {
//                            Log.i("CreateUser", "onComplete: Erro ao cadastrar o usuario");
//                        }
//                    }
//        });*/
//
//        /*Logar usuario no app
//        mAuth.signInWithEmailAndPassword(usuario, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (mAuth.getCurrentUser() != null){
//                    Log.i("singIn", "Sucesso ao logar usuario!");
//
//                }else {
//                    Log.i("singIn", "Erro ao efetuar o login");
//                }
//            }
//        });*/
//
//
//        /*Deslogar usuario do app
//        mAuth.signOut();
//
//        Verifica se usuario esta logado no app
//        if (mAuth.getCurrentUser() != null){
//            Log.i("UserStatus", "onComplete: Usuario está logado");
//
//        }else {
//            Log.i("UserStatus", "onComplete: Usuario não está logado");
//        }*/
//
//
//
//
//        /*
//
//        //RECUPERA DADOS
//        referencia.child("usuarios").child("0001").child("nome").setValue("Xurupita da Silva");
//        DatabaseReference usuarios = referencia.child("usuarios");
//        DatabaseReference produtos = referencia.child("produtos");
//
//
//        usuarios.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                Log.i("FIREBASE", "onDataChange: " + dataSnapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        */
//
//        //SALVAR DADOS NO FIREBASE
//        /*Usuario usuario = new Usuario();
//        usuario.setNome("Samara");
//        usuario.setSobrenome("Ogata");
//        usuario.setIdade(33);
//        usuarios.child("002").setValue(usuario);
//
//        Produto produto = new Produto();
//        produto.setMarca("Samsung");
//        produto.setTipoProduto("Celular");
//        produto.setDescricao("S10+");
//        produto.setPreco(2999.00);
//        produtos.child("001").setValue(produto);*/




    }
