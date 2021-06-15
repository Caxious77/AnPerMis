package es.ucm.fdi.applistclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private OnPermListener onPermListener;
    private List<Permission> permissionList;
    private String category;

    public PermissionAdapter(Context context, OnPermListener onPermListener, String category){
        this.layoutInflater = LayoutInflater.from(context);
        this.onPermListener = onPermListener;
        this.category = category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_perm_card, parent, false);
        return new ViewHolder(itemView, this, onPermListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Permission permission = permissionList.get(position);
        holder.permImage.setImageResource(permission.getPermissionImage());
        holder.permName.setText(permission.getName());
        holder.permComplete.setText(permission.getCompleteName());
        if(category.equals("SIN INFORMACION")){
            holder.permCircle.setVisibility(View.GONE);
            holder.permPorcent.setVisibility(View.GONE);
            holder.comentPorcent.setVisibility(View.GONE);
        }else{
            holder.comentPorcent.setText("Aparece en el " + (int)(permission.getPorcentaje()*100)+"% de las apps de esta categoría.");
            holder.permCircle.setImageResource(permission.getCircleImage());
            holder.permPorcent.setText((int)(permission.getPorcentaje()*100)+"%");
        }
    }

    @Override
    public int getItemCount() {
        return this.permissionList.size();
    }

    public void setPermissionList(List<Permission> permissionList){
        this.permissionList = permissionList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView permImage;
        public final TextView permName;
        public final TextView permComplete;
        public final ImageView permCircle;
        public final TextView permPorcent;
        public final TextView comentPorcent;
        private OnPermListener listener;

        public ViewHolder(@NonNull View itemView, PermissionAdapter adapter, OnPermListener listener) {
            super(itemView);
            this.permImage = itemView.findViewById(R.id.permIcon);
            this.permName = itemView.findViewById(R.id.permName);
            this.permComplete = itemView.findViewById(R.id.permComplete);
            this.permCircle = itemView.findViewById(R.id.permCircle);
            this.permPorcent = itemView.findViewById(R.id.permPorcen);
            this.comentPorcent = itemView.findViewById(R.id.comentarioPorcent);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onPermClick(getAdapterPosition());
        }
    }

    public interface OnPermListener{
        void onPermClick(int position);
    }
}
