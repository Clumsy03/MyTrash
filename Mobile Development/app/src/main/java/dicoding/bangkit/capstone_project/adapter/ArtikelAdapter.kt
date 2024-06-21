package dicoding.bangkit.capstone_project.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import dicoding.bangkit.capstone_project.Data.Data_artikel
import dicoding.bangkit.capstone_project.R

class ArtikelAdapter(private val listartikel : ArrayList<Data_artikel>) : RecyclerView.Adapter<ArtikelAdapter.listViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class listViewHolder(itemsView : View) : RecyclerView.ViewHolder(itemsView) {
        val imgphoto : ImageView = itemsView.findViewById(R.id.image_artikel)
        val judul : TextView = itemsView.findViewById(R.id.judul)
        val deskripsi1 : TextView = itemsView.findViewById(R.id.deskripsi_singkat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return listViewHolder(view)
    }

    override fun getItemCount(): Int = listartikel.size

    override fun onBindViewHolder(holder: listViewHolder, position: Int) {
       val (name, desripsi1, deskripsi2, deskripsi3, subjudul1, subjudul2, photo) = listartikel[position]
        holder.judul.text = name
        holder.deskripsi1.text = desripsi1
        holder.imgphoto.setImageResource(photo)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listartikel[holder.adapterPosition]) }
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Data_artikel)
    }
}