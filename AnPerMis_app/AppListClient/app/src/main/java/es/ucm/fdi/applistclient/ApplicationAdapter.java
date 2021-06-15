package es.ucm.fdi.applistclient;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private OnAppListener onAppListener;
    private List<Application> applicationList;

    public ApplicationAdapter(Context context, OnAppListener onAppListener){
        this.layoutInflater = LayoutInflater.from(context);
        this.onAppListener = onAppListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_app_card, parent, false);
        return new ApplicationAdapter.ViewHolder(itemView, this, onAppListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Application application = applicationList.get(position);
        Drawable appIcon = application.getAppIcon();
        appIcon.setBounds(0, 0, 150, 150);
        holder.icon.setCompoundDrawables(appIcon, null, null, null);
        holder.icon.setCompoundDrawablePadding(30);
        holder.name.setText(application.getName());
        holder.version.setText(application.getVersion());
    }

    @Override
    public int getItemCount() {
        return this.applicationList.size();
    }

    public void setApplicationList(List<Application> applicationList){
        this.applicationList = applicationList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView icon;
        public final TextView name;
        public final TextView version;
        private OnAppListener listener;

        public ViewHolder(@NonNull View itemView, ApplicationAdapter adapter, OnAppListener listener) {
            super(itemView);
            icon = itemView.findViewById(R.id.appIcon);
            name = itemView.findViewById(R.id.appName);
            version = itemView.findViewById(R.id.appVersion);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onAppClick(getAdapterPosition());
        }
    }

    public interface OnAppListener{
        void onAppClick(int position);
    }
}
