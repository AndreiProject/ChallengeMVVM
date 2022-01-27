package com.paramonov.challenge.ui.feature.main

import androidx.navigation.*
import com.paramonov.challenge.R

fun MainActivity.getNavController() = Navigation.findNavController(this, R.id.nav_host)

fun MainActivity.getNavOptions() = NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()