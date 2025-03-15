package gautam.sarthak.textextractor.di

import android.app.Application
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import gautam.sarthak.textextractor.repo.MainRepoImpl
import gautam.sarthak.textextractor.repository.MainRepo
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application):Context{
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideTextRecognizer():TextRecognizer{
        return TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    @Provides
    @Singleton
    fun provideClipBoardManager(context: Context):ClipboardManager{
        return context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    }

    @Provides
    @Singleton
    fun provideMainRepo(context: Context,textRecognizer: TextRecognizer,
                        clipboardManager: ClipboardManager):MainRepo{
        return MainRepoImpl(context,textRecognizer,clipboardManager)
    }
}