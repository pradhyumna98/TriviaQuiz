package com.example.pradhyumna.triviaquiz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {




    public AboutUsFragment() {
        // Required empty public constructor
    }

    public static AboutUsFragment newInstance() {

        AboutUsFragment about = new AboutUsFragment();
        return about;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ImageView ivMail,ivGit,ivFace;
        TextView tvgits,tvgitp,tvFaceS,tvFaceP;
        View output=inflater.inflate(R.layout.fragment_about_us, container, false);
        ivMail=output.findViewById(R.id.ivmail);
        ivGit=output.findViewById(R.id.ivgit);
        ivFace=output.findViewById(R.id.ivface);
        tvFaceS=output.findViewById(R.id.textView6);
        tvFaceP=output.findViewById(R.id.textView7);
        tvgitp=output.findViewById(R.id.textView3);
        tvgits=output.findViewById(R.id.textView2);
        tvFaceP.setMovementMethod(LinkMovementMethod.getInstance());
        String textfacep = "<a href='https://www.facebook.com/pradhyumna.purushottam'> https://www.facebook.com/pradhyumna.purushottam </a>";
        tvFaceP.setText(Html.fromHtml(textfacep));
        tvFaceS.setMovementMethod(LinkMovementMethod.getInstance());
        String textfaces = "<a href='https://www.facebook.com/sgoyal12'> https://www.facebook.com/sgoyal12 </a>";
        tvFaceS.setText(Html.fromHtml(textfaces));
        tvgitp.setMovementMethod(LinkMovementMethod.getInstance());
        String textgitp = "<a href='https://github.com/pradhyumna98'> https://github.com/pradhyumna98 </a>";
        tvgitp.setText(Html.fromHtml(textgitp));
        tvgits.setMovementMethod(LinkMovementMethod.getInstance());
        String textgits = "<a href='https://github.com/sgoyal12'>https://github.com/sgoyal12 </a>";
        tvgits.setText(Html.fromHtml(textgits));
        Picasso.get().load(R.drawable.mail).fit().into(ivMail);
        Picasso.get().load(R.drawable.github).fit().into(ivGit);
        Picasso.get().load(R.drawable.logofacebook).fit().into(ivFace);
        return output;
    }

}
