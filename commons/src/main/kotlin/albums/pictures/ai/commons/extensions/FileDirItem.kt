package albums.pictures.ai.commons.extensions

import android.content.Context
import albums.pictures.ai.commons.models.FileDirItem

fun FileDirItem.isRecycleBinPath(context: Context): Boolean {
    return path.startsWith(context.recycleBinPath)
}
