package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements Fragment2.OnFragment2DataListener,
                   Fragment3.OnFragment3CheckboxListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Start with Fragment1
        Fragment1 fragment1 = Fragment1.newInstance(null, null);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment1)
                .commit();

        // The Continue button in activity_main moves from Fragment1 to Fragment2
        binding.buttonContinue.setOnClickListener(view -> {
            Fragment2 fragment2 = Fragment2.newInstance(null, null);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment2)
                    .addToBackStack(null)
                    .commit();

            // Hide the activity-level Continue button — Fragment2 has its own
            binding.buttonContinue.setVisibility(android.view.View.GONE);
        });
    }

    // Called by Fragment2 via interface when user taps Continue in Fragment2
    @Override
    public void onNameSent(String name) {
        // Create Fragment3 and pass the name via Bundle
        Fragment3 fragment3 = Fragment3.newInstance(name);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment3)
                .addToBackStack(null)
                .commit();
    }

    // Called by Fragment3 via interface when checkbox state changes
    @Override
    public void onCheckboxChanged(boolean isChecked) {
        // The button enable/disable and text change are handled inside Fragment3 itself.
        // This callback is here to satisfy the interface contract and can be extended later.
    }
}
