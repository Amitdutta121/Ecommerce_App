package net.smallacademy.gridcycle;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> titles;
    List<String> images;
    List<String> prices;
    List<String> ids;

    LayoutInflater inflater;

    public Adapter(Context ctx, List<String> titles, List<String> images, List<String> prices, List<String> ids){
        this.titles = titles;
        this.images = images;
        this.prices = prices;
        this.ids = ids;
        this.inflater = LayoutInflater.from(ctx);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_grid_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.title.setText(titles.get(position));
//            holder.gridIcon.setImageResource(images.get(position));

            Picasso.get().load(images.get(position))
                    .resize(50,50)
                    .error(R.drawable.ic_aspect_ratio_black_24dp)
                    .into(holder.gridIcon);

            holder.price_view.setText(prices.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView gridIcon;
        TextView price_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            gridIcon = itemView.findViewById(R.id.imageView2);
            price_view = itemView.findViewById(R.id.price_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //id , image , price, title

                    ArrayList<String> passingData = new ArrayList<String>();
                    passingData.add(ids.get(getAdapterPosition()));
                    passingData.add(images.get(getAdapterPosition()));
                    passingData.add(prices.get(getAdapterPosition()));
                    passingData.add(titles.get(getAdapterPosition()));





//                    Toast.makeText(v.getContext(), "Clicked -> " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), SingleProduct.class);
//                    intent.putExtra("passingData", passingData.toArray());
                    intent.putStringArrayListExtra("data", passingData);

                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
